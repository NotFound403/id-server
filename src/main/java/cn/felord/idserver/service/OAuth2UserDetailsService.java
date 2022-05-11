package cn.felord.idserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface Oauth2 user details service.
 *
 * @author felord.cn
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @since 1.0.0
 */
@FunctionalInterface
public interface OAuth2UserDetailsService {
    /**
     * Load oauth2 user by username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    UserDetails loadOAuth2UserByUsername(String username) throws UsernameNotFoundException;
}
