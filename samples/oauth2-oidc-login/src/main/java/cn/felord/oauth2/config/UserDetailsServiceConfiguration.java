package cn.felord.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author felord.cn
 */
@EnableWebSecurity(debug = true)
public class UserDetailsServiceConfiguration {
    /**
     * 这里虚拟一个用户 felord 123456  随机密码
     *
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> User.withUsername("felord")
                .password("123456")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .build();
    }

}
