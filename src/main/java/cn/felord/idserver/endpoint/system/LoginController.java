package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.SystemSettings;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/system";
        }
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        SystemSettings systemSettings = new SystemSettings();

        model.addAttribute("systemSettings", systemSettings);
        return "/system/login";
    }

    @GetMapping("/system/logout")
    @ResponseBody
    public Rest logout(Model model,
                       @CurrentSecurityContext(expression = "authentication") Authentication authentication,
                       @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken, HttpSession session) {
        session.invalidate();
        return RestBody.ok("注销成功");
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
    public String home() {
        return "console/console";
    }

}
