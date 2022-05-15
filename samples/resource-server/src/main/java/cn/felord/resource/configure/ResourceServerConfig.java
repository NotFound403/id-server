package cn.felord.resource.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The type Resource server config.
 */
@EnableWebSecurity
public class ResourceServerConfig {
    /**
     * Security filter chain security filter chain.
     *
     * @param httpSecurity the http security
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/res/foo").hasAnyAuthority("SCOPE_message.read")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
        return httpSecurity.build();
    }
}
