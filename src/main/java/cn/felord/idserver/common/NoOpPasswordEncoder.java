package cn.felord.idserver.common;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 由于不同{@link org.springframework.security.oauth2.core.ClientAuthenticationMethod}之间的密码处理机制不一样，所以使用该密码机进行兼容。
 *
 * @author felord.cn
 * @since 1.0.0
 */
public final class NoOpPasswordEncoder implements PasswordEncoder {

    private static final PasswordEncoder INSTANCE = new NoOpPasswordEncoder();

    private NoOpPasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    /**
     * Get the singleton {@link  NoOpPasswordEncoder}.
     */
    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

}
