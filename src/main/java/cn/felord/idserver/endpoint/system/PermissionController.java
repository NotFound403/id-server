package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * The type PermissionController.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class PermissionController extends BaseController {
private final PermissionService permissionService;

    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/permission/main")
    public String main() {
        return "/system/permission/main";
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/system/permission/add")
    public String add() {
        return "/system/permission/add";
    }

    /**
     * Tree list.
     *
     * @return the list
     */
    @GetMapping("/system/permission/data")
    @ResponseBody
    public List<Permission> tree() {
        return permissionService.findByRoot();
    }

    /**
     * List list.
     *
     * @return the list
     */
    @GetMapping("/system/permission/list")
    @ResponseBody
    @PreAuthorize("hasPermission('permission','list')")
    public List<Permission> list() {
        return permissionService.findAll();
    }

    /**
     * Menu list list.
     *
     * @return the list
     */
    @GetMapping("/system/permission/parents")
    @ResponseBody
    public List<Permission> parentPermissions() {
        return permissionService.parents();
    }
}
