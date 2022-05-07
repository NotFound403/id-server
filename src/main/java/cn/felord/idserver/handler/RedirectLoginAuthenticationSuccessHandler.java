package cn.felord.idserver.handler;

import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * @author felord.cn
 * @since 1.0.0
 */
public class RedirectLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private static final String defaultTargetUrl = "/";
    private final String redirect;

    public RedirectLoginAuthenticationSuccessHandler() {
        this.redirect = defaultTargetUrl;
    }

    public RedirectLoginAuthenticationSuccessHandler(String redirect) {
        this.redirect = redirect;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);

        String targetUrl = savedRequest == null ? this.redirect : savedRequest.getRedirectUrl();
        clearAuthenticationAttributes(request);
        this.write(RestBody.okData(Collections.singletonMap("targetUrl", targetUrl), "登录成功！"), response);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    private void write(Rest<?> body, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resBody = objectMapper.writeValueAsString(body);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
