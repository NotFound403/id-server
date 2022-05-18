package cn.felord.authentication.captcha;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author n1
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "phone";

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login/captcha",
            "POST");

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    private Converter<HttpServletRequest, CaptchaAuthenticationToken> captchaAuthenticationTokenConverter;

    private boolean postOnly = true;


    public CaptchaAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.captchaAuthenticationTokenConverter = defaultConverter();
    }

    public CaptchaAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.captchaAuthenticationTokenConverter = defaultConverter();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        CaptchaAuthenticationToken authRequest = captchaAuthenticationTokenConverter.convert(request);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    private Converter<HttpServletRequest, CaptchaAuthenticationToken> defaultConverter() {
        return request -> {
            String username = request.getParameter(this.usernameParameter);
            username = (username != null) ? username.trim() : "";
            String captcha = request.getParameter(this.captchaParameter);
            captcha = (captcha != null) ? captcha.trim() : "";
            return new CaptchaAuthenticationToken(username, captcha);
        };
    }


    protected void setDetails(HttpServletRequest request, CaptchaAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(captchaParameter, "Password parameter must not be empty or null");
        this.captchaParameter = captchaParameter;
    }

    public void setConverter(Converter<HttpServletRequest, CaptchaAuthenticationToken> converter) {
        Assert.notNull(converter, "Converter must not be null");
        this.captchaAuthenticationTokenConverter = converter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getCaptchaParameter() {
        return this.captchaParameter;
    }
}
