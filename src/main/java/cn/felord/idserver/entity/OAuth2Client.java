package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Client.
 *
 * @author Steve Riesenberg
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "oauth2_client")
public class OAuth2Client implements Serializable {
    private static final long serialVersionUID = 8481969837769002598L;
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String id;
    @Column(name = "client_id", unique = true, updatable = false)
    private String clientId;

    private Instant clientIdIssuedAt;
    private String clientSecret;
    //todo
    private Instant clientSecretExpiresAt;
    private String clientName;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    private Set<ClientAuthMethod> clientAuthenticationMethods;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    private Set<OAuth2GrantType> authorizationGrantTypes;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    private Set<RedirectUri> redirectUris;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false)
    private Set<OAuth2Scope> scopes;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id",foreignKey =  @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private OAuth2ClientSettings clientSettings;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id",foreignKey =  @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private OAuth2TokenSettings tokenSettings;

    /**
     * To registered client registered client.
     *
     * @return the registered client
     */
    public RegisteredClient toRegisteredClient() {

        Set<ClientAuthMethod> clientAuthMethods = clientAuthenticationMethods == null ? Collections.emptySet() : clientAuthenticationMethods;
        Set<OAuth2GrantType> oAuth2GrantTypes = authorizationGrantTypes == null ? Collections.emptySet() : authorizationGrantTypes;
        Set<RedirectUri> uris = redirectUris == null ? Collections.emptySet() : redirectUris;
        Set<OAuth2Scope> oAuth2Scopes = scopes == null ? Collections.emptySet() : scopes;

        RegisteredClient.Builder builder = RegisteredClient.withId(Optional.ofNullable(this.id).orElse(UUID.randomUUID().toString()))
                .clientId(Optional.ofNullable(this.clientId).orElse(UUID.randomUUID().toString()))
                .clientIdIssuedAt(this.clientIdIssuedAt)
                .clientSecretExpiresAt(this.clientSecretExpiresAt)
                .clientName(this.clientName)
                .clientAuthenticationMethods(clientAuthenticationMethodSet ->
                        clientAuthenticationMethodSet.addAll(clientAuthMethods.stream()
                                .map(ClientAuthMethod::toAuthenticationMethod)
                                .collect(Collectors.toSet())))
                .authorizationGrantTypes(authorizationGrantTypeSet ->
                        authorizationGrantTypeSet.addAll(oAuth2GrantTypes.stream()
                                .map(OAuth2GrantType::toGrantType)
                                .collect(Collectors.toSet())))
                .redirectUris(redirectUriSet -> redirectUriSet.addAll(uris.stream()
                        .map(RedirectUri::getRedirectUri)
                        .collect(Collectors.toSet())))
                .scopes(scopeSet -> scopeSet.addAll(oAuth2Scopes.stream()
                        .map(OAuth2Scope::getScope)
                        .collect(Collectors.toSet())))
                .clientSettings(this.clientSettings.toClientSettings())
                .tokenSettings(this.tokenSettings.toTokenSettings());

        if (StringUtils.hasText(this.clientSecret)) {
            // CLIENT_SECRET_JWT 使用密文   其它使用明文
            String encoded = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientSecret);
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
        final String clientId = registeredClient.getClientId();
        oAuth2Client.setClientId(clientId);
        oAuth2Client.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        //ignore clientSecret clientSecretExpiresAt
        oAuth2Client.setClientSecret(registeredClient.getClientSecret());
        oAuth2Client.setClientName(registeredClient.getClientName());


        oAuth2Client.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods()
                .stream()
                .map(method -> {
                    ClientAuthMethod clientAuthMethod = new ClientAuthMethod();
                    clientAuthMethod.setClientId(clientId);
                    clientAuthMethod.setClientAuthenticationMethod(method.getValue());
                    return clientAuthMethod;
                })
                .collect(Collectors.toSet()));
        oAuth2Client.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(grantType -> {
                    OAuth2GrantType oAuth2GrantType = new OAuth2GrantType();
                    oAuth2GrantType.setClientId(clientId);
                    oAuth2GrantType.setGrantTypeName(grantType.getValue());
                    return oAuth2GrantType;
                })
                .collect(Collectors.toSet()));
        oAuth2Client.setRedirectUris(registeredClient.getRedirectUris().stream()
                .map(redirectUri -> {
                    RedirectUri uri = new RedirectUri();
                    uri.setClientId(clientId);
                    uri.setRedirectUri(redirectUri);
                    return uri;
                })
                .collect(Collectors.toSet()));
        oAuth2Client.setScopes(registeredClient.getScopes().stream().map(scope -> {
                    OAuth2Scope oAuth2Scope = new OAuth2Scope();
                    oAuth2Scope.setClientId(clientId);
                    oAuth2Scope.setScope(scope);
                    return oAuth2Scope;
                })
                .collect(Collectors.toSet()));
        OAuth2ClientSettings settings = OAuth2ClientSettings.fromClientSettings(registeredClient.getClientSettings());
        settings.setClientId(clientId);
        oAuth2Client.setClientSettings(settings);
        OAuth2TokenSettings oAuth2TokenSettings = OAuth2TokenSettings.fromTokenSettings(registeredClient.getTokenSettings());
        oAuth2TokenSettings.setClientId(clientId);
        oAuth2Client.setTokenSettings(oAuth2TokenSettings);
        return oAuth2Client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OAuth2Client OAuth2Client = (OAuth2Client) o;
        return id != null && Objects.equals(id, OAuth2Client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

