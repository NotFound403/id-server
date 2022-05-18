package cn.felord.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author felord.cn
 */
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

    /***
     * 自定义
     *
     * @param http http
     * @return SecurityFilterChain
     * @throws Exception exception
     */
    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                        .antMatchers("/foo/bar")
                        .hasAnyAuthority("ROLE_ANONYMOUS")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2clientLogin->
                        oauth2clientLogin.loginPage("/oauth2/authorization/felord"))
                .oauth2Client().and().exceptionHandling();
        return http.build();
    }

    @Bean
    WebSecurityCustomizer ignore() {
        return web -> web.ignoring().antMatchers("/favicon.ico");
    }
}


