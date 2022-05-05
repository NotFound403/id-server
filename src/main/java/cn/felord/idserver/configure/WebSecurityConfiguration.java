package cn.felord.idserver.configure;

import cn.felord.idserver.handler.RedirectLoginAuthenticationSuccessHandler;
import cn.felord.idserver.handler.SimpleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;

/**
 * The Web security configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    /**
     * 管理后台以{@code /system}开头
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     * @see AuthorizationServerConfiguration
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE+1)
    SecurityFilterChain systemSecurityFilterChain(HttpSecurity http) throws Exception {
        SimpleAuthenticationEntryPoint authenticationEntryPoint = new SimpleAuthenticationEntryPoint();

        AuthenticationEntryPointFailureHandler authenticationFailureHandler = new AuthenticationEntryPointFailureHandler(authenticationEntryPoint);
        http.antMatcher("/system/**").csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests().anyRequest().anonymous()
                /*  .and()
                  .exceptionHandling()
                  .authenticationEntryPoint(authenticationEntryPoint)*/
                .and()
                .formLogin().loginPage("/system/login").loginProcessingUrl("/system/login")
                .successHandler(new RedirectLoginAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler).permitAll()
                .and()
                .oauth2ResourceServer().jwt();
        return http.build();
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
                .antMatchers("/pear.config.json")
                .antMatchers("/pear.config.yml")
                .antMatchers("/admin/css/**")
                .antMatchers("/admin/fonts/**")
                .antMatchers("/admin/js/**")
                .antMatchers("/admin/images/**")
                .antMatchers("/favicon.ico");
    }
}
