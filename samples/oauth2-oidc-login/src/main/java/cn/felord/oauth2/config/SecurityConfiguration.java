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
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                // 避免 favicon.ico 404 被 save request 引发的重定向错误
                .oauth2Login(oauth2clientLogin ->
                        oauth2clientLogin.loginPage("/oauth2/authorization/felord"));
        return http.build();
    }

    @Bean
    WebSecurityCustomizer ignore() {
        return web -> web.ignoring().antMatchers("/favicon.ico");
    }
}


