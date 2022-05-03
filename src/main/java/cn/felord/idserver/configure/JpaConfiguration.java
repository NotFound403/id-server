package cn.felord.idserver.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
        // @Entity
        // @EntityListeners(AuditingEntityListener.class)
        // public class Demo {
        //
        //    @CreatedDate
        //    private Instant createTime;
        //
        //    @CreatedBy
        //    private String createId;
        //
        //    @LastModifiedDate
        //    private Instant updateTime;
        //
        //    @LastModifiedBy
        //    private String updateId;
        // }
        return () -> {

            String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(principal);
        };
    }

}
