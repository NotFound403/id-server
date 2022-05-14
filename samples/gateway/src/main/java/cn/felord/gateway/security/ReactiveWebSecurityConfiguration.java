package cn.felord.gateway.security;

import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.endpoint.NimbusJwtClientAuthenticationParametersConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.ReactiveOAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * The type Reactive web security configuration.
 */
@EnableWebFluxSecurity
public class ReactiveWebSecurityConfiguration {
    /**
     * Spring security filter chain security web filter chain.
     *
     * @param http the http
     * @return the security web filter chain
     */
    @Bean
    @ConditionalOnMissingBean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().anyExchange().authenticated();
        http.oauth2Login();
        http.oauth2Client();
        return http.build();
    }

    /**
     *  兼容CLIENT SECRET JWT高端玩法
     *
     * @return the reactive o auth 2 access token response client
     */
    @Bean
    public ReactiveOAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> oAuth2AccessTokenResponseClient() {

        NimbusJwtClientAuthenticationParametersConverter<OAuth2AuthorizationCodeGrantRequest> clientAuthenticationParametersConverter = new NimbusJwtClientAuthenticationParametersConverter<>(clientRegistration -> {
            if (ClientAuthenticationMethod.CLIENT_SECRET_JWT.equals(clientRegistration.getClientAuthenticationMethod())) {
                SecretKeySpec secretKey = new SecretKeySpec(
                        clientRegistration.getClientSecret().getBytes(StandardCharsets.UTF_8),
                        "HmacSHA256");
                return new OctetSequenceKey.Builder(secretKey)
                        .keyID(UUID.randomUUID().toString())
                        .build();
            }
            return null;
        });
        WebClientReactiveAuthorizationCodeTokenResponseClient tokenResponseClient = new WebClientReactiveAuthorizationCodeTokenResponseClient();
        tokenResponseClient.addParametersConverter(clientAuthenticationParametersConverter);
        return tokenResponseClient;
    }
}
