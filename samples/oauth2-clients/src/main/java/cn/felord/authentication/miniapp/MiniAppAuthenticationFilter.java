package cn.felord.authentication.miniapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class MiniAppAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login/miniapp",
            "POST");
    private final ObjectMapper om = new ObjectMapper();
    private Converter<HttpServletRequest, MiniAppAuthenticationToken> miniAppAuthenticationTokenConverter;
    private boolean postOnly = true;

    public MiniAppAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.miniAppAuthenticationTokenConverter = defaultConverter();
    }

    public MiniAppAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.miniAppAuthenticationTokenConverter = defaultConverter();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        MiniAppAuthenticationToken authRequest = miniAppAuthenticationTokenConverter.convert(request);
        if (authRequest == null) {
            throw new BadCredentialsException("fail to extract miniapp authentication request params");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, MiniAppAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setConverter(Converter<HttpServletRequest, MiniAppAuthenticationToken> converter) {
        Assert.notNull(converter, "Converter must not be null");
        this.miniAppAuthenticationTokenConverter = converter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    private Converter<HttpServletRequest, MiniAppAuthenticationToken> defaultConverter() {
        return request -> {
            try (BufferedReader reader = request.getReader()) {
                MiniAppRequest miniAppRequest = this.om.readValue(reader, MiniAppRequest.class);
                return new MiniAppAuthenticationToken(miniAppRequest);
            } catch (IOException e) {
                return null;
            }
        };
    }
}
