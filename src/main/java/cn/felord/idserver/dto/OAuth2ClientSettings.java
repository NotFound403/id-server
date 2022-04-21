package cn.felord.idserver.dto;

import lombok.Data;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.util.StringUtils;

/**
 * The type O auth 2 client settings.
 *
 * @author felord.cn
 * @see ClientSettings
 * @since 1.0.0
 */
@Data
public class OAuth2ClientSettings {
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
        if (algorithm!=null){
            oAuth2ClientSettings.setSigningAlgorithm(algorithm.getName());
        }
        return oAuth2ClientSettings;
    }
}
