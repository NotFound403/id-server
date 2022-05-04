package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.Role;
import cn.felord.idserver.service.JpaRoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Role controller.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class RoleController extends BaseController {

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

    /**
     * Add rest.
     *
     * @param role the role
     * @return the rest
     */
    @PostMapping("/system/role/add")
    @ResponseBody
    public Rest<?> add(@RequestBody Role role) {
        role.setClientId("0");
        jpaRoleService.save(role);
        return RestBody.ok("操作成功");
    }

    /**
     * Page page.
     *
     * @param page  the page
     * @param limit the limit
     * @return the page
     */
    @GetMapping("/system/role/data")
    @ResponseBody
    public Page<Role> page(@RequestParam Integer page, @RequestParam Integer limit) {
        return jpaRoleService.page(page, limit);
    }

}
