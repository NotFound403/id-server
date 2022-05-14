package cn.felord.resource.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {
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
