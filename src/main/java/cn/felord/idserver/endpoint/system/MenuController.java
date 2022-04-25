package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.service.JpaMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 查询菜单
     *
     * @return the list
     */
    @GetMapping("/system/menu/data")
    @ResponseBody
    public List<Menu> menuList(){
        return jpaMenuService.findByRoot();
    }
}
