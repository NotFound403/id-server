package cn.felord.idserver.configure;

import cn.felord.idserver.authentication.LoginFilterSecurityConfigurer;
import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.entity.Role;
import cn.felord.idserver.enumate.RootUserConstants;
import cn.felord.idserver.handler.RedirectLoginAuthenticationSuccessHandler;
import cn.felord.idserver.handler.SimpleAuthenticationEntryPoint;
import cn.felord.idserver.service.OAuth2UserDetailsService;
import cn.felord.idserver.service.UserInfoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

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
     * The constant ID_SERVER_SYSTEM_SECURITY_CONTEXT_KEY.
     */
    public static final String ID_SERVER_SYSTEM_SECURITY_CONTEXT_KEY = "ID_SERVER_SYSTEM_SECURITY_CONTEXT";
    public static final String ID_SERVER_SYSTEM_SAVED_REQUEST_KEY = "ID_SERVER_SYSTEM_SECURITY__SAVED_REQUEST";

    /**
     * ?????????????????????
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class AuthorizationServerConfiguration {

        /**
         * Authorization server ?????? ?????????????????????
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
            //  ???????????????????????????URI????????????
            authorizationServerConfigurer.authorizationEndpoint(authorizationEndpointConfigurer ->
                    authorizationEndpointConfigurer.consentPage(CUSTOM_CONSENT_PAGE_URI));

            RequestMatcher authorizationServerEndpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

            // ?????? ????????????????????????????????????
            http.requestMatcher(authorizationServerEndpointsMatcher)
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    // ????????????????????????csrf
                    .csrf(csrf -> csrf
                            .ignoringRequestMatchers(authorizationServerEndpointsMatcher))
                    .formLogin()
                    .and()
                    // ?????? ????????????????????????
                    .apply(authorizationServerConfigurer)
                    .and()
                    .oauth2ResourceServer().jwt();
            return http.build();
        }

        /**
         * ?????? OAuth2.0 provider?????????
         *
         * @param port the port
         * @return the provider settings
         * @since 1.0.0
         */
        @Bean
        public ProviderSettings providerSettings(@Value("${server.port}") Integer port) {
            //TODO ????????? ????????????????????????
            return ProviderSettings.builder().issuer("http://localhost:" + port).build();
        }
    }

    /**
     * ??????????????????.
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class SystemSecurityConfiguration {

        /**
         * ???????????????{@code /system}??????
         *
         * @param http the http
         * @return the security filter chain
         * @throws Exception the exception
         * @see AuthorizationServerConfiguration
         */
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE + 1)
        SecurityFilterChain systemSecurityFilterChain(HttpSecurity http, UserInfoService userInfoService) throws Exception {
            SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
            AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
            HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
            securityContextRepository.setSpringSecurityContextKey(ID_SERVER_SYSTEM_SECURITY_CONTEXT_KEY);
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            requestCache.setSessionAttrName(ID_SERVER_SYSTEM_SAVED_REQUEST_KEY);
            http.antMatcher(SYSTEM_ANT_PATH).csrf().disable()
                    .headers().frameOptions().sameOrigin()
                    .and()
                    .requestCache().requestCache(requestCache)
                    .and()
                    .securityContext().securityContextRepository(securityContextRepository)
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    /*  .and()
                      .exceptionHandling()
                      .authenticationEntryPoint(authenticationEntryPoint)*/
                    .and()
                    .userDetailsService(userInfoService::findByUsername)
                    .formLogin().loginPage("/system/login").loginProcessingUrl("/system/login")
                    .successHandler(new RedirectLoginAuthenticationSuccessHandler("/system", requestCache))
                    .failureHandler(authenticationFailureHandler).permitAll();
            return http.build();
        }

    }

    /**
     * ??????????????????????????????.
     *
     * @author felord.cn
     * @since 1.0.0
     */
    @Configuration(proxyBeanMethods = false)
    public static class OAuth2SecurityConfiguration {

        /**
         * Default security filter chain security filter chain.
         *
         * @param http                     the http
         * @param oAuth2UserDetailsService the oauth2 user details service
         * @param securityFilterChain      the security filter chain
         * @return the security filter chain
         * @throws Exception the exception
         */
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE + 2)
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                       OAuth2UserDetailsService oAuth2UserDetailsService,
                                                       @Qualifier("authorizationServerSecurityFilterChain") SecurityFilterChain securityFilterChain) throws Exception {
            DefaultSecurityFilterChain authorizationServerFilterChain = (DefaultSecurityFilterChain) securityFilterChain;
            SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();
            AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
            RedirectLoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler = new RedirectLoginAuthenticationSuccessHandler();
            http.requestMatcher(new AndRequestMatcher(
                            new NegatedRequestMatcher(new AntPathRequestMatcher(SYSTEM_ANT_PATH)),
                            new NegatedRequestMatcher(authorizationServerFilterChain.getRequestMatcher())
                    )).authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .anyRequest().authenticated()
                    ).csrf().disable()
                    .userDetailsService(oAuth2UserDetailsService::loadOAuth2UserByUsername)
                    .formLogin().loginPage("/login")
                    .successHandler(loginAuthenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler).permitAll()
                    .and()
                    .apply(new LoginFilterSecurityConfigurer<>())
                    // ??????????????????????????????
                    .captchaLogin(captchaLoginConfigurer->
                                    // ??????????????? 1 ??????????????? ??????????????? 2 ?????????Spring Bean ???????????????
                                    captchaLoginConfigurer.captchaService(this::verifyCaptchaMock)
                                            // ???????????????????????????UserDetials  1 ??????????????? ??????????????? 2 ?????????Spring Bean ???????????????
                                            .captchaUserDetailsService(this::loadUserByPhoneMock)
                                            // ????????????????????????
                                            .successHandler(loginAuthenticationSuccessHandler)
                                            // ????????????????????????
                                            .failureHandler(authenticationFailureHandler)
                            );
            return http.build();
        }


        private boolean verifyCaptchaMock(String phone, String code) {
            return code.equals("1234");
        }

        private UserDetails loadUserByPhoneMock(String phone) throws UsernameNotFoundException {
            return  // ?????????
                    User.withUsername(phone)
                            // ??????
                            .password("password")
                            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                            .roles("user", "mobile")
                            .build();
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

    /**
     * ????????????.
     *
     * @return the permission evaluator
     */
    @Bean
    PermissionEvaluator permissionEvaluator() {
        return new ResourcePermissionEvaluator();
    }


    /**
     * The type Resource permission evaluator.
     */
    static class ResourcePermissionEvaluator implements PermissionEvaluator {


        @Override
        @SuppressWarnings("unchecked")
        public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
            final String permissionCode = targetDomainObject + ":" + permission;
            Collection<Role> roles = (Collection<Role>) authentication.getAuthorities();
            if (roles.stream().anyMatch(role -> Objects.equals(RootUserConstants.ROOT_ROLE_NAME.val(), role.getRoleName()))) {
                return true;
            }
            return roles.stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(Permission::getPermissionCode)
                    .anyMatch(p -> Objects.equals(p, permissionCode));
        }

        @Override
        public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
            throw new IllegalArgumentException("????????????");
        }
    }
}
