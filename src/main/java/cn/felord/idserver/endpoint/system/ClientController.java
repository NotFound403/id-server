package cn.felord.idserver.endpoint.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/list")
    public String index() {
        return "client/list";
    }
}
