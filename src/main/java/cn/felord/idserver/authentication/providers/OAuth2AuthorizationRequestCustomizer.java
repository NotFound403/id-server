package cn.felord.idserver.authentication.providers;

import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * customizer {@link OAuth2AuthorizationRequest}
 * <p>
 * client_id 变成 appid ，并追加锚点#wechat_redirect
 *
 * @author felord.cn
 * @see DefaultOAuth2AuthorizationRequestResolver#setAuthorizationRequestCustomizer(Consumer)
 */
public class OAuth2AuthorizationRequestCustomizer {

    /**
     * 授权请求参数定制
     *
     * @param builder the builder
     */
    public static void customize(OAuth2AuthorizationRequest.Builder builder) {
        builder.attributes(attributes ->
                Arrays.stream(ClientProviders.values())
                        .filter(clientProvider ->
                                Objects.equals(clientProvider.registrationId(),
                                        attributes.get(OAuth2ParameterNames.REGISTRATION_ID)))
                        .findAny()
                        .map(ClientProviders::requestConsumer)
                        .ifPresent(requestConsumer ->
                                requestConsumer.accept(builder)));
    }

}
