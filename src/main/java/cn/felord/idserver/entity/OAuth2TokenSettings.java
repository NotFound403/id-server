package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;

/**
 * The type O auth 2 client token settings.
 *
 * @author felord.cn
 * @see TokenSettings
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "oauth2_token_settings")
public class OAuth2TokenSettings implements Serializable {
    private static final long serialVersionUID = -7077164876986169673L;
    @Id
    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientId;
    private Duration accessTokenTimeToLive;
    private String tokenFormat;
    private boolean reuseRefreshTokens = true;
    private Duration refreshTokenTimeToLive;
    private String idTokenSignatureAlgorithm;


    /**
     * To token settings token settings.
     *
     * @return the token settings
     */
    public TokenSettings toTokenSettings() {

        return TokenSettings.builder()
                .accessTokenTimeToLive(Optional.ofNullable(this.accessTokenTimeToLive)
                        .orElse(Duration.ofMinutes(5)))
                .accessTokenFormat(Optional.ofNullable(tokenFormat)
                        .map(OAuth2TokenFormat::new)
                        .orElse(OAuth2TokenFormat.SELF_CONTAINED))
                .reuseRefreshTokens(this.reuseRefreshTokens)
                .refreshTokenTimeToLive(Optional.ofNullable(this.refreshTokenTimeToLive)
                        .orElse(Duration.ofMinutes(60)))
                .idTokenSignatureAlgorithm(Optional.ofNullable(idTokenSignatureAlgorithm)
                        .map(SignatureAlgorithm::from)
                        .orElse(SignatureAlgorithm.RS256))
                .build();

    }

    /**
     * From tokenSettings to oauth2TokenSettings.
     *
     * @param tokenSettings the tokenSettings
     * @return the oauth2TokenSettings
     */
    public static OAuth2TokenSettings fromTokenSettings(TokenSettings tokenSettings) {
        OAuth2TokenSettings oAuth2TokenSettings = new OAuth2TokenSettings();
        oAuth2TokenSettings.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
        oAuth2TokenSettings.setTokenFormat(tokenSettings.getAccessTokenFormat().getValue());
        oAuth2TokenSettings.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        oAuth2TokenSettings.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        oAuth2TokenSettings.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());
        return oAuth2TokenSettings;
    }


}
