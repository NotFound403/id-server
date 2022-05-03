package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.service.JpaRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Role controller.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class RoleController {

    private final JpaRoleService jpaRoleService;
    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/role/main")
    public String main() {
        return "/system/role/main";
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/system/role/add")
    public String add() {
        return "/system/role/add";
    }
}
