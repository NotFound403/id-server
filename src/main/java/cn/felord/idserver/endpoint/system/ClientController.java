package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.dto.OAuth2Client;
import cn.felord.idserver.entity.Client;
import cn.felord.idserver.service.JpaRegisteredClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Client controller.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@AllArgsConstructor
@Controller
@RequestMapping("/client")
public class ClientController extends BaseController {
    private JpaRegisteredClientRepository clientRepository;

    /**
     * Index string.
     *
     * @return the string
     */
    @GetMapping("/list")
    public String index() {
        return "client/list";
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/add")
    public String add() {
        return "client/add";
    }

    /**
     * Page page.
     *
     * @param page  the page
     * @param limit the limit
     * @return the page
     */
    @GetMapping("/pagination")
    @ResponseBody
    public Page<OAuth2Client> page(@RequestParam Integer page, @RequestParam Integer limit) {
        return clientRepository.page(PageRequest.of(page, limit, Sort.sort(Client.class)
                .by(Client::getClientIdIssuedAt).descending()));
    }
}
