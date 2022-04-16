package cn.felord.idserver.entity;

import lombok.Data;

/**
 * The Oauth2 scope.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class OAuth2Scope {
    private String scope;
    public String description;
}
