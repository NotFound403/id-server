package cn.felord.idserver.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * The Authorization server configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * Authorization server 集成 优先级要高一些
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     * @since 1.0.0
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        //  把自定义的授权确认URI加入配置
        authorizationServerConfigurer.authorizationEndpoint(authorizationEndpointConfigurer ->
                authorizationEndpointConfigurer.consentPage(CUSTOM_CONSENT_PAGE_URI));

        RequestMatcher authorizationServerEndpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        // 拦截 授权服务器相关的请求端点
        http.requestMatcher(authorizationServerEndpointsMatcher)
                .authorizeRequests().anyRequest().authenticated()
                .and()
                // 忽略掉相关端点的csrf
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(authorizationServerEndpointsMatcher))
                // 开启form登录
                .formLogin()
                .and()
                // 应用 授权服务器的配置
                .apply(authorizationServerConfigurer);
        return http.build();
    }

    /**
     * 配置 OAuth2.0 provider元信息
     *
     * @return the provider settings
     * @since 1.0.0
     */
    @Bean
    public ProviderSettings providerSettings(@Value("${server.port}") Integer port) {
        //TODO 生产应该使用域名
        return ProviderSettings.builder().issuer("http://localhost:" + port).build();
    }
}
