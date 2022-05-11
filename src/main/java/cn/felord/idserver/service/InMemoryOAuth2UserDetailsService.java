package cn.felord.idserver.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.HashMap;
import java.util.Map;
/**
 * The type InMemoryOAuth2UserDetailsService.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public class InMemoryOAuth2UserDetailsService implements OAuth2UserDetailsService {
    private final Map<String, UserDetails> users = new HashMap<>();

    public InMemoryOAuth2UserDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("user")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("USER")
                .build();
        this.users.put(userDetails.getUsername(), userDetails);
    }

    @Override
    public UserDetails loadOAuth2UserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.users.get(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }
}
