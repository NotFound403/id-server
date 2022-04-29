package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type O auth 2 client settings.
 *
 * @author felord.cn
 * @see ClientSettings
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "oauth2_client_settings")
public class OAuth2ClientSettings implements Serializable {
    private static final long serialVersionUID = -7956711700342643896L;
    @Id
    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientId;
    private boolean requireProofKey;
    private boolean requireAuthorizationConsent;
    private String jwkSetUrl;
    private String signingAlgorithm;


    /**
     * To client settings client settings.
     *
     * @return the client settings
     */
    public ClientSettings toClientSettings() {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireProofKey(this.requireProofKey)
                .requireAuthorizationConsent(this.requireAuthorizationConsent);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.from(this.signingAlgorithm);
        JwsAlgorithm jwsAlgorithm = signatureAlgorithm == null ? MacAlgorithm.from(this.signingAlgorithm) : signatureAlgorithm;
        if (jwsAlgorithm != null) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm);
        }
        if (StringUtils.hasText(this.jwkSetUrl)) {
            builder.jwkSetUrl(jwkSetUrl);
        }
        return builder.build();
    }

    /**
     * From clientSettings to oauth2ClientSettings.
     *
     * @param clientSettings the clientSettings
     * @return the oauth2ClientSettings
     */
    public static OAuth2ClientSettings fromClientSettings(ClientSettings clientSettings) {
        OAuth2ClientSettings oAuth2ClientSettings = new OAuth2ClientSettings();
        oAuth2ClientSettings.setRequireProofKey(clientSettings.isRequireProofKey());
        oAuth2ClientSettings.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        oAuth2ClientSettings.setJwkSetUrl(clientSettings.getJwkSetUrl());
        JwsAlgorithm algorithm = clientSettings.getTokenEndpointAuthenticationSigningAlgorithm();
        if (algorithm != null) {
            oAuth2ClientSettings.setSigningAlgorithm(algorithm.getName());
        }
        return oAuth2ClientSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OAuth2ClientSettings that = (OAuth2ClientSettings) o;
        return clientId != null && Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
