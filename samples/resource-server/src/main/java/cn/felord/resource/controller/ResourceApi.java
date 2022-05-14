package cn.felord.resource.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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
    public Map<String,Object> foo(@CurrentSecurityContext(expression="authentication") Authentication authentication){
        return Collections.singletonMap("hello",authentication.getAuthorities());
    }
}
