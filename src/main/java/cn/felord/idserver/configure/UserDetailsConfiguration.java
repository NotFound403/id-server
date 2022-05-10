package cn.felord.idserver.configure;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.enumate.Gender;
import cn.felord.idserver.service.InMemoryOAuth2UserDetailsService;
import cn.felord.idserver.service.OAuth2UserDetailsService;
import cn.felord.idserver.service.UserInfoService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;


/**
 * The User details configuration.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class UserDetailsConfiguration {
    private static final String ROOT_USERNAME = "root";


    /**
     * Users user details service.
     *
     * @return the user details service
     */
    @Bean
    UserDetailsService systemUserDetailsService(UserInfoService userInfoService) {
        return new RootUserDetailsService(userInfoService);
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


    static final class RootUserDetailsService implements UserDetailsService {
        private static final String ROOT_USE_ID = "root_user_id";
        private static final String ROOT_RAW_PASSWORD = "idserver";
        private static final String ROOT_ROLE_NAME = "id_server";
        private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        private final UserInfoService userInfoService;


        public RootUserDetailsService(UserInfoService userInfoService) {
            this.userInfoService = userInfoService;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (!ROOT_USERNAME.equals(username)) {
                return userInfoService.findByUsername(username);
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(ROOT_USE_ID);
            userInfo.setUsername(ROOT_USERNAME);
            userInfo.setPassword(PASSWORD_ENCODER.encode(ROOT_RAW_PASSWORD));
            userInfo.setNickName(ROOT_ROLE_NAME);
            userInfo.setRealName(ROOT_ROLE_NAME);
            userInfo.setEnabled(Boolean.TRUE);
            userInfo.setGender(Integer.valueOf(Gender.UNKNOWN.val()));
            Role rootRole = new Role();
            // only roleName
            rootRole.setRoleName(ROOT_ROLE_NAME);
            userInfo.setAuthorities(Collections.singleton(rootRole));
            return userInfo;
        }
    }

}
