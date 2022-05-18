package cn.felord.authentication.captcha;

/**
 * @author n1
 */
public interface CaptchaService {

    /**
     * verify captcha
     *
     * @param phone   phone
     * @param rawCode rawCode
     * @return isVerified
     */
    boolean verifyCaptcha(String phone, String rawCode);
}
