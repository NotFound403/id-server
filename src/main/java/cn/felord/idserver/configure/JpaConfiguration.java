package cn.felord.idserver.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * JpaConfiguration
 *
 * @author zhengchalei
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

    /**
     * 审计
     *
     * @return {@link AuditorAware}<{@link String}>
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            String name = authentication.getName();
            return Optional.of(name);
        };
    }

}
