package cn.felord.idserver.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * The User details configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class UserDetailsConfiguration {
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("felord")
                .password("123456")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
