package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.ClientAuthMethod;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.OAuth2GrantType;
import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.entity.RedirectUri;
import cn.felord.idserver.entity.dto.OAuth2ClientDTO;
import cn.felord.idserver.service.OAuth2ClientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @PreAuthorize("hasPermission('client','add')")
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
    @PreAuthorize("hasPermission('client','update')")
    public String edit(Model model, @PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);
        String oAuth2Scope = oauth2Client.getScopes().stream().map(OAuth2Scope::getScope).collect(Collectors.joining(","));
        String redirectUris = oauth2Client.getRedirectUris().stream().map(RedirectUri::getRedirectUri).collect(Collectors.joining(","));
        model.addAttribute("oauth2Client", oauth2Client);
        model.addAttribute("oauth2Scope", oAuth2Scope);
        model.addAttribute("redirectUris", redirectUris);
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
    @PreAuthorize("hasPermission('client','add')")
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
    @PreAuthorize("hasPermission('client','remove')")
    public Rest<?> remove(@PathVariable String id) {
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
    @PreAuthorize("hasPermission('client','update')")
    public Rest<?> edit(@RequestBody OAuth2ClientDTO oAuth2Client) {
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
    @PreAuthorize("hasPermission('client','list')")
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
    @PreAuthorize("hasPermission('client','list')")
    public String details(Model model, @PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);
        model.addAttribute("oauth2Client", oauth2Client);
        return "/system/client/details";
    }

    /**
     * Details string.
     *
     * @param model the model
     * @param id    the id
     * @return the string
     */
    @GetMapping("/system/client/yaml/{id}")
    @PreAuthorize("hasPermission('client','list')")
    public String yaml(Model model, @PathVariable String id) {
        OAuth2Client oauth2Client = clientRepository.findClientById(id);

        String clientName = oauth2Client.getClientName();
        String clientId = oauth2Client.getClientId();

        Set<RedirectUri> redirectUris = oauth2Client.getRedirectUris();
        String uris = redirectUris.stream()
                .map(RedirectUri::getRedirectUri)
                .collect(Collectors.joining(","));
        Set<OAuth2GrantType> authorizationGrantTypes = oauth2Client.getAuthorizationGrantTypes();
        String types = authorizationGrantTypes.stream()
                .map(OAuth2GrantType::getGrantTypeName)
                .collect(Collectors.joining(","));
        String method = oauth2Client.getClientAuthenticationMethods().stream()
                .map(ClientAuthMethod::getClientAuthenticationMethod)
                .collect(Collectors.joining(","));
        String scopes = Stream.concat(
                        oauth2Client.getScopes().stream()
                                .map(OAuth2Scope::getScope), Stream.of(OidcScopes.OPENID))
                .collect(Collectors.joining(","));
        LinkedHashMap<String, Object> client = new LinkedHashMap<>();
        LinkedHashMap<String, Object> clientRegistration = new LinkedHashMap<>();
        clientRegistration.put("client-id", clientId);
        clientRegistration.put("client-secret", "请填写您记忆的OAuth2客户端密码");
        clientRegistration.put("redirect-uri", "请从" + uris + "指定一个");
        clientRegistration.put("authorization-grant-type", "请从 " + types + " 指定一个");
        clientRegistration.put("client-authentication-method", method);
        clientRegistration.put("scope", scopes);
        client.put("registration",
                Collections.singletonMap(clientName, clientRegistration));
        client.put("provider", Collections.singletonMap(clientName,
                Collections.singletonMap("issuer-uri", "http://localhost:9000")));


        Map<String, Object> spring =
                Collections.singletonMap("spring",
                        Collections.singletonMap("security",
                                Collections.singletonMap("oauth2",
                                        Collections.singletonMap("client", client))));


        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        String dump = yaml.dump(spring);
        model.addAttribute("yaml", dump);
        return "/system/client/yaml";
    }

}
