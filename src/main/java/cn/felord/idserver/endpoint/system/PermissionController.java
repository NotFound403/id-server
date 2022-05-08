package cn.felord.idserver.endpoint.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type PermissionController.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class PermissionController {

    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/permission/main")
    public String main() {
        return "/system/menu/main";
    }
}
