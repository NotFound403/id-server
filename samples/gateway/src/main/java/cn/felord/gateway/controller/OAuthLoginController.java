package cn.felord.gateway.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

/**
 * The type O auth login controller.
 */
@RestController
public class OAuthLoginController {


    /**
     * 仅供演示，authentication中的敏感信息不应暴露给前端。
     *
     * @param authentication the authentication
     * @return the mono
     */
    @GetMapping("/")
    public Mono<Map<String, Object>> index(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return Mono.justOrEmpty(Collections.singletonMap("currentAuthentication", authentication));
    }


}
