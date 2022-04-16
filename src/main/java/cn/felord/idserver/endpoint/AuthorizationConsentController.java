package cn.felord.idserver.endpoint;

import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.service.OAuth2Service;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义用户确认页
 *
 * @author felord.cn
 */
@Controller
public class AuthorizationConsentController {
    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    private final OAuth2Service scopeService;

    public AuthorizationConsentController(RegisteredClientRepository registeredClientRepository,
                                          OAuth2AuthorizationConsentService authorizationConsentService,
                                          OAuth2Service scopeService) {
        this.registeredClientRepository = registeredClientRepository;
        this.authorizationConsentService = authorizationConsentService;
        this.scopeService = scopeService;
    }

    /**
     * {@link OAuth2AuthorizationEndpointFilter} 会302重定向到{@code  /oauth2/consent}并携带入参
     *
     * @param principal 当前用户
     * @param model     视图模型
     * @param clientId  oauth2 client id
     * @param scope     请求授权的scope
     * @param state     state 值
     * @return 自定义授权确认页面 consent.html
     */
    @GetMapping(value = "/oauth2/consent")
    public String consent(Principal principal, Model model,
                          @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                          @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                          @RequestParam(OAuth2ParameterNames.STATE) String state) {

        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        String id = registeredClient.getId();
        OAuth2AuthorizationConsent currentAuthorizationConsent =
                this.authorizationConsentService.findById(id, principal.getName());

        Set<String> authorizedScopes = currentAuthorizationConsent != null ?
                currentAuthorizationConsent.getScopes() : Collections.emptySet();

        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();

        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope);
            } else {
                scopesToApprove.add(requestedScope);
            }
        }

        Set<OAuth2Scope> scopesToApproves = scopeService.findByNames(scopesToApprove);
        Set<OAuth2Scope> previouslyApprovedScopesSet = scopeService.findByNames(previouslyApprovedScopes);

        String clientName = registeredClient.getClientName();
        model.addAttribute("clientId", clientId);
        model.addAttribute("clientName", clientName);
        model.addAttribute("state", state);
        model.addAttribute("scopes", scopesToApproves);
        model.addAttribute("previouslyApprovedScopes", previouslyApprovedScopesSet);
        model.addAttribute("principalName", principal.getName());

        return "consent";
    }
}
