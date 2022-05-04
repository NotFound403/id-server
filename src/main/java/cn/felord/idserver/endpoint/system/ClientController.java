package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.dto.OAuth2ClientDTO;
import cn.felord.idserver.service.OAuth2ClientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final OAuth2ClientService clientRepository;

    /**
     * 客户端列表页
     *
     * @return the string
     */
    @GetMapping("/system/client/main")
    public String main() {
        return "system/client/main";
    }

    /**
     * 新增客户端页
     *
     * @return the string
     */
    @GetMapping("/system/client/add")
    public String add() {
        return "/system/client/add";
    }

    /**
     * Add oauth2 client rest.
     *
     * @param oAuth2Client the o auth 2 client
     * @return the rest
     */
    @PostMapping("/system/client/add")
    @ResponseBody
    public Rest<?> addOAuth2Client(@RequestBody OAuth2ClientDTO oAuth2Client) {
        clientRepository.saveClient(oAuth2Client);
        return RestBody.ok("操作成功");
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
        page = Math.max(page - 1, 0);
        return clientRepository.page(PageRequest.of(page, limit, Sort.sort(OAuth2Client.class)
                .by(OAuth2Client::getClientIdIssuedAt).descending()));
    }

    /**
     * Details o auth 2 client.
     *
     * @param id the id
     * @return the o auth 2 client
     */
    @GetMapping("/system/client/details/{id}")
    public String details(Model model, @PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);
        model.addAttribute("oauth2Client",oauth2Client);
        return "/system/client/details";

    }
}
