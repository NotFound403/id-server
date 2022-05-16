package cn.felord.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试OAuth2的控制器
 *
 * @author felord.cn
 */
@Slf4j
@RestController
public class FooController {

    /**
     * 默认登录成功跳转页为 /  防止404状态
     *
     * @return the map
     */
    @GetMapping("/")
    public Map<String, Object> index(@RegisteredOAuth2AuthorizedClient
                                                 OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> map = new HashMap<>(2);

        // OAuth2AuthorizedClient 为敏感信息不应该返回前端
        log.debug("OAuth2AuthorizedClient：{} ",oAuth2AuthorizedClient);
        map.put("authentication", authentication);
        return map;
    }
}
