package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.service.JpaRegisteredClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ClientController extends BaseController {
    private JpaRegisteredClientRepository clientRepository;

    /**
     * Index string.
     *
     * @return the string
     */
    @GetMapping("/system/client/list")
    public String index() {
        return "client/list";
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/system/client/add")
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
    @GetMapping("/system/client/data")
    @ResponseBody
    public Page<OAuth2Client> page(@RequestParam Integer page, @RequestParam Integer limit) {
        return clientRepository.page(PageRequest.of(page, limit, Sort.sort(OAuth2Client.class)
                .by(OAuth2Client::getClientIdIssuedAt).descending()));
    }
}
