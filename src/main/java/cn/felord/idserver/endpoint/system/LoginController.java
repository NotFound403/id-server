package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.entity.SystemSettings;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * The type Login endpoint.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
public class LoginController {

    /**
     * Login page string.
     *
     * @return the string
     */
    @GetMapping("/system/login")
    public String loginPage(Model model,
                            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                            @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken) {

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/system";
        }
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        SystemSettings systemSettings = new SystemSettings();

        model.addAttribute("systemSettings", systemSettings);
        return "/system/login";
    }

    @GetMapping("/system")
    public String index() {
        return "/system/index";
    }

    /**
     * Home string.
     *
     * @return the string
     */
    @GetMapping("/system/console")
    public String home(){
        return "console/console";
    }

}
