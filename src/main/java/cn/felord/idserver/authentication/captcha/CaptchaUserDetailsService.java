package cn.felord.idserver.authentication.captcha;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author felord
 */
public interface CaptchaUserDetailsService {
    /**
     * load user by phone
     *
     * @param phone phone
     * @return userDetails
     * @throws UsernameNotFoundException not found user
     */
    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;
}
