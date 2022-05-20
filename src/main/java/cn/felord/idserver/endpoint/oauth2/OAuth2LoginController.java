package cn.felord.idserver.endpoint.oauth2;

import cn.felord.idserver.entity.SystemSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * The type OAuth2LoginController.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
public class OAuth2LoginController {

    /**
     * Oauth 2 login page string.
     *
     * @param model              the model
     * @param authentication     the authentication
     * @param enableCaptchaLogin the enable captcha login
     * @param csrfToken          the csrf token
     * @return the string
     */
    @GetMapping("/login")
    public String oauth2LoginPage(Model model,
                              @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                              @Value("${spring.security.oauth2.server.login.captcha.enabled:true}") boolean enableCaptchaLogin,
                              @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken) {

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/";
        }
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        SystemSettings systemSettings = new SystemSettings();
        model.addAttribute("enableCaptchaLogin",enableCaptchaLogin);
        model.addAttribute("systemSettings", systemSettings);
        return "oauth2_login";
    }


    /**
     * oauth2中间页.
     *
     * @param model          the model
     * @param authentication the authentication
     * @param csrfToken      the csrf token
     * @return the string
     */
    @GetMapping("/")
    public String oauth2IndexPage(Model model,
                                  @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                                  @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken) {

        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        model.addAttribute("principal",authentication.getName());
        SystemSettings systemSettings = new SystemSettings();

        model.addAttribute("systemSettings", systemSettings);
        return "oauth2_index";
    }

}
