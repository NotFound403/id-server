package cn.felord.idserver.entity.dto;

import cn.felord.idserver.entity.ClientAuthMethod;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.OAuth2ClientSettings;
import cn.felord.idserver.entity.OAuth2GrantType;
import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.entity.OAuth2TokenSettings;
import cn.felord.idserver.entity.RedirectUri;
import lombok.Data;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Oauth2 client dto.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class OAuth2ClientDTO {
    private String clientSecret;
    private String clientName;
    private Set<String> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes;
    private Set<String> scopes;
    private Set<String> redirectUris;
    private OAuth2ClientSettings clientSettings;
    private OAuth2TokenSettings tokenSettings;


    /**
     * 密码在此处被hash，其它地方不再对密码进行处理
     *
     * @return the o auth 2 client
     */
    public OAuth2Client toClient() {

        OAuth2Client oAuth2Client = new OAuth2Client();

        final String clientId = UUID.randomUUID().toString();
        oAuth2Client.setClientId(clientId);
        oAuth2Client.setClientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientSecret));
        oAuth2Client.setClientName(clientName);
        oAuth2Client.setClientAuthenticationMethods(clientAuthenticationMethods.stream().map(method->{
            ClientAuthMethod clientAuthMethod = new ClientAuthMethod();
            clientAuthMethod.setClientId(clientId);
            clientAuthMethod.setClientAuthenticationMethod(method);
            return clientAuthMethod;
        }).collect(Collectors.toSet()));
        oAuth2Client.setAuthorizationGrantTypes(authorizationGrantTypes.stream().map(grantType->{
            OAuth2GrantType oAuth2GrantType = new OAuth2GrantType();
            oAuth2GrantType.setClientId(clientId);
            oAuth2GrantType.setGrantTypeName(grantType);
            return oAuth2GrantType;
        }).collect(Collectors.toSet()));
        // openid 忽略
        oAuth2Client.setScopes(scopes.stream().filter(scope-> !OidcScopes.OPENID.equals(scope))
                .map(scope->{
            OAuth2Scope oAuth2Scope = new OAuth2Scope();
            oAuth2Scope.setClientId(clientId);
            oAuth2Scope.setScope(scope);
            return oAuth2Scope;
        }).collect(Collectors.toSet()));
        oAuth2Client.setRedirectUris(redirectUris.stream().map(redirectUri->{
            RedirectUri oAuth2RedirectUri = new RedirectUri();
            oAuth2RedirectUri.setClientId(clientId);
            oAuth2RedirectUri.setRedirectUri(redirectUri);
            return oAuth2RedirectUri;
        }).collect(Collectors.toSet()));
        if (clientSettings==null){
            clientSettings= OAuth2ClientSettings.fromClientSettings(new OAuth2ClientSettings().toClientSettings());
            clientSettings.setClientId(clientId);
        }
        if (tokenSettings==null){
            tokenSettings= OAuth2TokenSettings.fromTokenSettings(new OAuth2TokenSettings().toTokenSettings());
            tokenSettings.setClientId(clientId);
        }
        oAuth2Client.setClientSettings(clientSettings);
        oAuth2Client.setTokenSettings(tokenSettings);
        return oAuth2Client;

    }


}
