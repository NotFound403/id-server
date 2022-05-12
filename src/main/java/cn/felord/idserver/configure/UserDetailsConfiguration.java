package cn.felord.idserver.configure;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.enumate.Gender;
import cn.felord.idserver.enumate.RootUserConstants;
import cn.felord.idserver.service.InMemoryOAuth2UserDetailsService;
import cn.felord.idserver.service.OAuth2UserDetailsService;
import cn.felord.idserver.service.RootUserDetailsService;
import cn.felord.idserver.service.UserInfoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;


/**
 * The UserDetails configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class UserDetailsConfiguration {

    /**
     * Users user details service.
     *
     * @param userInfoService        the user info service
     * @param rootUserDetailsService the root user details service
     * @return the user details service
     */
    @Bean
    UserDetailsService systemUserDetailsService(UserInfoService userInfoService, RootUserDetailsService rootUserDetailsService) {
        return username -> RootUserConstants.ROOT_USERNAME.val().equals(username) ?
                rootUserDetailsService.loadRootUserByUsername(username) : userInfoService.findByUsername(username);
    }

    /**
     * 默认授权用户接口的实现.
     *
     * @return the oauth2 user details service
     */
    @Bean
    @ConditionalOnMissingBean
    OAuth2UserDetailsService oAuth2UserDetailsService() {
        return new InMemoryOAuth2UserDetailsService();
    }

    /**
     * 加载root用户，你可以通过实现{@link RootUserDetailsService}进行覆盖
     *
     * @return the root user details service
     */
    @Bean
    @ConditionalOnMissingBean
    RootUserDetailsService rootUserDetailsService() {
        return new InMemoryRootUserDetailsService();
    }

    /**
     * The type In memory root user details service.
     */
    static final class InMemoryRootUserDetailsService implements RootUserDetailsService {
        private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        @Override
        public UserDetails doLoadRootUser(String username) {
            String rootUserName = RootUserConstants.ROOT_USERNAME.val();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(RootUserConstants.ROOT_USE_ID.val());
            userInfo.setUsername(rootUserName);
            userInfo.setPassword(PASSWORD_ENCODER.encode(RootUserConstants.ROOT_RAW_PASSWORD.val()));
            userInfo.setNickName(rootUserName);
            userInfo.setRealName(rootUserName);
            userInfo.setEnabled(Boolean.TRUE);
            userInfo.setGender(Integer.valueOf(Gender.UNKNOWN.val()));
            Role rootRole = new Role();
            // only roleName
            rootRole.setRoleName(RootUserConstants.ROOT_ROLE_NAME.val());
            userInfo.setAuthorities(Collections.singleton(rootRole));
            return userInfo;
        }
    }

}
