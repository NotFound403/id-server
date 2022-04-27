package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.service.JpaMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * The type Menu controller.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class MenuController extends BaseController {
    private final JpaMenuService jpaMenuService;

    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/menu/main")
    public String main() {
        return "/system/menu/main";
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/system/menu/add")
    public String add() {
        return "/system/menu/add";
    }

    /**
     * Add rest.
     *
     * @param menu the menu
     * @return the rest
     */
    @PostMapping("/system/menu/add")
    public Rest<?> add(@RequestBody Menu menu) {
        jpaMenuService.save(menu);
        return RestBody.ok("操作成功");
    }

    /**
     * Edit string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/system/menu/update")
    public String edit(@RequestParam String id, Model model) {
        Menu menu = jpaMenuService.findById(id);
        model.addAttribute("menu", menu);
        return "/system/menu/edit";
    }

    /**
     * Edit rest.
     *
     * @param menu the menu
     * @return the rest
     */
    @PostMapping("/system/menu/update")
    public Rest<?> edit(@RequestBody Menu menu) {
        jpaMenuService.update(menu);
        return RestBody.ok("操作成功");
    }

    /**
     * 查询菜单
     *
     * @return the list
     */
    @GetMapping("/system/menu/data")
    @ResponseBody
    public List<Menu> menuList() {
        return jpaMenuService.findByRoot();
    }

}
