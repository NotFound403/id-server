package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.entity.RedirectUri;
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

import java.util.stream.Collectors;

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
     * 编辑页
     *
     * @param model the model
     * @param id    the id
     * @return the string
     */
    @GetMapping("/system/client/edit/{id}")
    public String edit(Model model,@PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);
        String oAuth2Scope = oauth2Client.getScopes().stream().map(OAuth2Scope::getScope).collect(Collectors.joining(","));
        String redirectUris = oauth2Client.getRedirectUris().stream().map(RedirectUri::getRedirectUri).collect(Collectors.joining(","));
        model.addAttribute("oauth2Client",oauth2Client);
        model.addAttribute("oauth2Scope",oAuth2Scope);
        model.addAttribute("redirectUris",redirectUris);
        return "/system/client/edit";
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
     * Remove rest.
     *
     * @param id the id
     * @return the rest
     */
    @PostMapping("/system/client/remove/{id}")
    @ResponseBody
    public Rest<?> remove(@PathVariable String id){
        clientRepository.removeByClientId(id);
        return RestBody.ok("操作成功");
    }

    /**
     * Remove rest.
     *
     * @param oAuth2Client the o auth 2 client
     * @return the rest
     */
    @PostMapping("/system/client/edit")
    @ResponseBody
    public Rest<?> edit(@RequestBody OAuth2ClientDTO oAuth2Client){
        clientRepository.update(oAuth2Client);
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
     * @param model the model
     * @param id    the id
     * @return the o auth 2 client
     */
    @GetMapping("/system/client/details/{id}")
    public String details(Model model, @PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);
        model.addAttribute("oauth2Client",oauth2Client);
        return "/system/client/details";
    }

}
