package cn.felord.idserver.authentication;


import cn.felord.idserver.authentication.captcha.CaptchaLoginFilterConfigurer;
import cn.felord.idserver.authentication.miniapp.MiniAppLoginFilterConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.DefaultSecurityFilterChain;


/**
 * The type Login filter security configurer.
 *
 * @param <H> the type parameter
 */
public class LoginFilterSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, H> {
    private CaptchaLoginFilterConfigurer<H> captchaLoginFilterConfigurer;
    private MiniAppLoginFilterConfigurer<H> miniAppLoginFilterConfigurer;

    /**
     * Captcha login captcha login filter configurer.
     *
     * @return the captcha login filter configurer
     */
    public CaptchaLoginFilterConfigurer<H> captchaLogin() {
        return lazyInitCaptchaLoginFilterConfigurer();
    }

    /**
     * Captcha login login filter security configurer.
     *
     * @param captchaLoginFilterConfigurerCustomizer the captcha login filter configurer customizer
     * @return the login filter security configurer
     */
    public LoginFilterSecurityConfigurer<H> captchaLogin(Customizer<CaptchaLoginFilterConfigurer<H>> captchaLoginFilterConfigurerCustomizer) {
        captchaLoginFilterConfigurerCustomizer.customize(lazyInitCaptchaLoginFilterConfigurer());
        return this;
    }

    /**
     * Mini app login mini app login filter configurer.
     *
     * @return the mini app login filter configurer
     */
    public MiniAppLoginFilterConfigurer<H> miniAppLogin() {
        return lazyInitMiniAppLoginFilterConfigurer();
    }

    /**
     * Mini app login login filter security configurer.
     *
     * @param miniAppLoginFilterConfigurerCustomizer the mini app login filter configurer customizer
     * @return the login filter security configurer
     */
    public LoginFilterSecurityConfigurer<H> miniAppLogin(Customizer<MiniAppLoginFilterConfigurer<H>> miniAppLoginFilterConfigurerCustomizer) {
        miniAppLoginFilterConfigurerCustomizer.customize(lazyInitMiniAppLoginFilterConfigurer());
        return this;
    }

    @Override
    public void init(H builder) throws Exception {
        if (captchaLoginFilterConfigurer != null) {
            captchaLoginFilterConfigurer.init(builder);
        }
        if (miniAppLoginFilterConfigurer != null) {
            miniAppLoginFilterConfigurer.init(builder);
        }
    }

    @Override
    public void configure(H builder) throws Exception {
        if (captchaLoginFilterConfigurer != null) {
            captchaLoginFilterConfigurer.configure(builder);
        }
        if (miniAppLoginFilterConfigurer != null) {
            miniAppLoginFilterConfigurer.configure(builder);
        }
    }

    private CaptchaLoginFilterConfigurer<H> lazyInitCaptchaLoginFilterConfigurer() {
        if (captchaLoginFilterConfigurer == null) {
            this.captchaLoginFilterConfigurer = new CaptchaLoginFilterConfigurer<>(this);
        }
        return captchaLoginFilterConfigurer;
    }

    private MiniAppLoginFilterConfigurer<H> lazyInitMiniAppLoginFilterConfigurer() {
        if (miniAppLoginFilterConfigurer == null) {
            this.miniAppLoginFilterConfigurer = new MiniAppLoginFilterConfigurer<>(this);
        }
        return miniAppLoginFilterConfigurer;
    }
}
