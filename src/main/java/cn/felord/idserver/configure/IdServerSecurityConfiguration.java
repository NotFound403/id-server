package cn.felord.idserver.configure;

import cn.felord.idserver.handler.RedirectLoginAuthenticationSuccessHandler;
import cn.felord.idserver.handler.SimpleAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * The type Id server security configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IdServerSecurityConfiguration {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";
    private static final String SYSTEM_ANT_PATH = "/system/**";

    /**
     * 授权服务器配置
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class AuthorizationServerConfiguration {

        /**
         * Authorization server 集成 优先级要高一些
         *
         * @param http the http
         * @return the security filter chain
         * @throws Exception the exception
         * @since 1.0.0
         */
        @Bean("authorizationServerSecurityFilterChain")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
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
                    .formLogin()
                    .and()
                    // 应用 授权服务器的配置
                    .apply(authorizationServerConfigurer);
            return http.build();
        }

        /**
         * 配置 OAuth2.0 provider元信息
         *
         * @param port the port
         * @return the provider settings
         * @since 1.0.0
         */
        @Bean
        public ProviderSettings providerSettings(@Value("${server.port}") Integer port) {
            //TODO 生产应该使用域名
            return ProviderSettings.builder().issuer("http://localhost:" + port).build();
        }
    }

    /**
     * 后台安全配置.
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class SystemSecurityConfiguration {

        /**
         * 管理后台以{@code /system}开头
         *
         * @param http the http
         * @return the security filter chain
         * @throws Exception the exception
         * @see AuthorizationServerConfiguration
         */
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE + 1)
        SecurityFilterChain systemSecurityFilterChain(HttpSecurity http) throws Exception {
            SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
            AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
            http.antMatcher(SYSTEM_ANT_PATH).csrf().disable()
                    .headers().frameOptions().sameOrigin()
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    /*  .and()
                      .exceptionHandling()
                      .authenticationEntryPoint(authenticationEntryPoint)*/
                    .and()
                    .formLogin().loginPage("/system/login").loginProcessingUrl("/system/login")
                    .successHandler(new RedirectLoginAuthenticationSuccessHandler("/system"))
                    .failureHandler(authenticationFailureHandler).permitAll();
            return http.build();
        }

    }

    /**
     * 后台安全配置.
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class OAuth2SecurityConfiguration {

        /**
         * Default security filter chain security filter chain.
         *
         * @param http the http
         * @return the security filter chain
         * @throws Exception the exception
         */
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE + 2)
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                       @Qualifier("authorizationServerSecurityFilterChain") SecurityFilterChain securityFilterChain) throws Exception {
            DefaultSecurityFilterChain authorizationServerFilterChain = (DefaultSecurityFilterChain) securityFilterChain;
            SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
            AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
            http.requestMatcher(new AndRequestMatcher(
                            new NegatedRequestMatcher(new AntPathRequestMatcher(SYSTEM_ANT_PATH)),
                            new NegatedRequestMatcher(authorizationServerFilterChain.getRequestMatcher())
                    )).authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .anyRequest().authenticated()
                    ).csrf().disable()
                    .userDetailsService(username -> User.builder()
                            .username("user")
                            .password("user")
                            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                            .roles("USER")
                            .build())
                    .formLogin().loginPage("/login")
                    .successHandler(new RedirectLoginAuthenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler).permitAll()
                    .and()
                    .oauth2ResourceServer().jwt();
            return http.build();
        }

    }


    /**
     * Web security customizer web security customizer.
     *
     * @return the web security customizer
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/component/**")
                .antMatchers("/actuator/health")
                .antMatchers("/h2-console/**")
                .antMatchers("/pear.config.json")
                .antMatchers("/pear.config.yml")
                .antMatchers("/admin/css/**")
                .antMatchers("/admin/fonts/**")
                .antMatchers("/admin/js/**")
                .antMatchers("/admin/images/**")
                .antMatchers("/favicon.ico");
    }
}
