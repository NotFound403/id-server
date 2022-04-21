package cn.felord.idserver.dto;

import lombok.Data;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.Version;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Oauth2 client.
 *
 * @author felord.cn
 * @see RegisteredClient
 * @since 1.0.0
 */
@Data
public class OAuth2Client implements Serializable {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    //todo
    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<String> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private OAuth2ClientSettings clientSettings;
    private OAuth2TokenSettings tokenSettings;

    /**
     * To registered client registered client.
     *
     * @return the registered client
     */
    public RegisteredClient toRegisteredClient() {
        Assert.notEmpty(clientAuthenticationMethods, "clientAuthenticationMethods must not be empty");
        Assert.notEmpty(authorizationGrantTypes, "authorizationGrantTypes must not be empty");
        Assert.notEmpty(scopes, "scopes must not be empty");

        RegisteredClient.Builder builder = RegisteredClient.withId(Optional.ofNullable(id).orElse(UUID.randomUUID().toString()))
                .clientId(Optional.ofNullable(clientId).orElse(UUID.randomUUID().toString()))
                .clientName(this.clientName)
                .clientAuthenticationMethods(clientAuthenticationMethodSet ->
                        clientAuthenticationMethodSet.addAll(clientAuthenticationMethods.stream()
                                .map(ClientAuthenticationMethod::new)
                                .collect(Collectors.toSet())))
                .authorizationGrantTypes(authorizationGrantTypeSet ->
                        authorizationGrantTypeSet.addAll(authorizationGrantTypes.stream()
                                .map(AuthorizationGrantType::new)
                                .collect(Collectors.toSet())))
                .redirectUris(redirectUriSet -> redirectUriSet.addAll(redirectUris))
                .scopes(scopeSet -> scopeSet.addAll(scopes))
                .clientSettings(this.clientSettings.toClientSettings())
                .tokenSettings(this.tokenSettings.toTokenSettings());

        if (StringUtils.hasText(clientSecret)) {
            // CLIENT_SECRET_JWT
            String encoded = clientAuthenticationMethods.contains(ClientAuthenticationMethod.CLIENT_SECRET_JWT.getValue()) ? clientSecret :
                    PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientSecret);
            builder.clientSecret(encoded);
        }

        return builder.build();
    }

    /**
     * From registeredClient to oauth2Client.
     *
     * @param registeredClient the registeredClient
     * @return the oauth2Client
     */
    public static OAuth2Client fromRegisteredClient(RegisteredClient registeredClient) {
        OAuth2Client oAuth2Client = new OAuth2Client();
        oAuth2Client.setId(registeredClient.getId());
        oAuth2Client.setClientId(registeredClient.getClientId());
        oAuth2Client.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        //ignore clientSecret clientSecretExpiresAt
        oAuth2Client.setClientName(registeredClient.getClientName());
        oAuth2Client.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.toSet()));
        oAuth2Client.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet()));
        oAuth2Client.setRedirectUris(registeredClient.getRedirectUris());
        oAuth2Client.setScopes(registeredClient.getScopes());
        oAuth2Client.setClientSettings(OAuth2ClientSettings.fromClientSettings(registeredClient.getClientSettings()));
        oAuth2Client.setTokenSettings(OAuth2TokenSettings.fromTokenSettings(registeredClient.getTokenSettings()));
        return oAuth2Client;
    }

}
