package cn.felord.idserver.endpoint;

import cn.felord.idserver.entity.SystemSettings;
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
    @GetMapping("/login")
    public String loginPage(Model model, @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken) {
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        SystemSettings systemSettings = new SystemSettings();

        model.addAttribute("systemSettings", systemSettings);
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
