package cn.felord.idserver.endpoint.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
