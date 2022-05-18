package cn.felord.idserver.cookie;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.WebUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于cookie的安全上下问存储
 *
 * @author felord.cn
 * @see org.springframework.security.web.context.HttpSessionSecurityContextRepository
 * @since 1.0.0
 */
public class CookieSecurityContextRepository implements SecurityContextRepository {
    /**
     * The constant SPRING_SECURITY_CONTEXT_KEY.
     */
    public static final String ID_SERVER_COOKIE = "ID_SERVER_COOKIE";
    /**
     * The Logger.
     */
    protected final Log logger = LogFactory.getLog(this.getClass());
    private Map<String, SecurityContext> authenticationMap = new ConcurrentHashMap<>();
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final String springSecurityContextKey;
    //todo
    private final String hmacKey = "xxxxxx";

    private static final String HMAC_SHA_512 = "HmacSHA512";

    private boolean disableUrlRewriting = false;

    /**
     * Instantiates a new Cookie security context repository.
     */
    public CookieSecurityContextRepository() {
        this(ID_SERVER_COOKIE);
    }

    /**
     * Instantiates a new Cookie security context repository.
     *
     * @param springSecurityContextKey the spring security context key
     */
    public CookieSecurityContextRepository(String springSecurityContextKey) {
        this.springSecurityContextKey = springSecurityContextKey;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpServletResponse response = requestResponseHolder.getResponse();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        this.readCookieFromRequest(request).ifPresent(cookie -> {
            SecurityContext securityContext = authenticationMap.get(springSecurityContextKey);
            if (securityContext!=null&&verifyCookie(securityContext,cookie)){
                Authentication authentication = securityContext.getAuthentication();
                context.setAuthentication(authentication);
            }
        });

        SaveToCookieResponseWrapper wrappedResponse = new SaveToCookieResponseWrapper(request, response, context);
        requestResponseHolder.setResponse(wrappedResponse);
        requestResponseHolder.setRequest(new SaveToCookieRequestWrapper(request, wrappedResponse));
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        SaveToCookieResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
                SaveToCookieResponseWrapper.class);
        Assert.state(responseWrapper != null, () -> "Cannot invoke saveContext on response " + response
                + ". You must use the HttpRequestResponseHolder.response after invoking loadContext");
        responseWrapper.saveContext(context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return this.readCookieFromRequest(request).isPresent();
    }


    private Optional<Cookie> readCookieFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies ->
                        Arrays.stream(cookies)
                                .filter(cookie ->
                                        springSecurityContextKey.equals(cookie.getName()))
                                .findFirst());
    }

    private static final class SaveToCookieRequestWrapper extends HttpServletRequestWrapper {

        private final SaveContextOnUpdateOrErrorResponseWrapper response;

        SaveToCookieRequestWrapper(HttpServletRequest request, SaveContextOnUpdateOrErrorResponseWrapper response) {
            super(request);
            this.response = response;
        }

        @Override
        public AsyncContext startAsync() {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync();
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
                throws IllegalStateException {
            this.response.disableSaveOnResponseCommitted();
            return super.startAsync(servletRequest, servletResponse);
        }

    }

    /**
     * The type Save to cookie response wrapper.
     */
    private final class SaveToCookieResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {
        private final Log logger = CookieSecurityContextRepository.this.logger;
        private final HttpServletRequest request;
        private final SecurityContext contextBeforeExecution;
        private final Authentication authBeforeExecution;

        private boolean isSaveContextInvoked;

        SaveToCookieResponseWrapper(HttpServletRequest request, HttpServletResponse response, SecurityContext context) {
            super(response, disableUrlRewriting);
            this.request = request;
            this.contextBeforeExecution = context;
            this.authBeforeExecution = context.getAuthentication();
        }

        @Override
        protected void saveContext(SecurityContext context) {
            final Authentication authentication = context.getAuthentication();
            if (authentication == null
                    || trustResolver.isAnonymous(authentication)) {
                if (this.authBeforeExecution != null) {
                    // SEC-1587 A non-anonymous context may still be in the session
                    // SEC-1735 remove if the contextBeforeExecution was not anonymous
                    authenticationMap.remove(springSecurityContextKey);
                    this.isSaveContextInvoked = true;
                }
                if (this.logger.isDebugEnabled()) {
                    if (authentication == null) {
                        this.logger.debug("Did not store empty SecurityContext");
                    } else {
                        this.logger.debug("Did not store anonymous SecurityContext");
                    }
                }
                return;
            }
          if (contextChanged(context)||authenticationMap.get(springSecurityContextKey)==null){
              String hmac = hmac(authentication.getName());
              Cookie cookie = new Cookie(springSecurityContextKey, hmac);
              cookie.setSecure(request.isSecure());
              cookie.setPath("/system");
              cookie.setHttpOnly(true);
              cookie.setMaxAge(60);
              cookie.setVersion(2);
              authenticationMap.put(springSecurityContextKey, context);
              this.addCookie(cookie);
          }
        }
        private boolean contextChanged(SecurityContext context) {
            return this.isSaveContextInvoked || context != this.contextBeforeExecution
                    || context.getAuthentication() != this.authBeforeExecution;
        }

    }

    @SneakyThrows
    private String hmac(String input) {
        Mac mac = Mac.getInstance(HMAC_SHA_512);
        SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA_512);
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(hmacBytes);
    }


    private boolean verifyCookie(SecurityContext securityContext,Cookie cookie){
        Authentication authentication = securityContext.getAuthentication();
        if (authentication!=null){
            String output = this.hmac(authentication.getName());
            return Objects.equals(cookie.getValue(), output);
        }
        return false;
    }

}
