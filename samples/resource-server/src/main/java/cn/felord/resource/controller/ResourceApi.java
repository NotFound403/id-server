package cn.felord.resource.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Resource api.
 */
@RestController
@RequestMapping("/res")
public class ResourceApi {

    /**
     * 受限访问资源，需要经过授权.
     *
     * @param authentication the authentication
     * @return the map
     */
    @GetMapping("/foo")
    public Map<String, Object> foo(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        String scopesAuthorized = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        map.put("authorized scopes", scopesAuthorized);
        map.put("app", "this is a resource server uri");
        return map;
    }
}
