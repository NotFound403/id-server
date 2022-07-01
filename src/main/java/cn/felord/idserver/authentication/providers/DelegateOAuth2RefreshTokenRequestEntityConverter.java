package cn.felord.idserver.authentication.providers;

import com.nimbusds.oauth2.sdk.GrantType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 兼容微信登录 {@code  https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN}
 */
public class DelegateOAuth2RefreshTokenRequestEntityConverter implements Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> {
    private static final String REFRESH_TOKEN_ENDPOINT = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private final Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> requestEntityConverter = new OAuth2RefreshTokenGrantRequestEntityConverter();

    @Override
    public RequestEntity<?> convert(OAuth2RefreshTokenGrantRequest source) {
        ClientRegistration clientRegistration = source.getClientRegistration();
        if (ClientProviders.WECHAT_WEB_LOGIN_CLIENT.registrationId().equals(clientRegistration.getClientId())) {
            MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
            queryParameters.add(WechatParameterNames.APP_ID, clientRegistration.getClientId());
            queryParameters.add(OAuth2ParameterNames.GRANT_TYPE, GrantType.REFRESH_TOKEN.getValue());
            queryParameters.add(OAuth2ParameterNames.REFRESH_TOKEN, source.getRefreshToken().getTokenValue());
            URI uri = UriComponentsBuilder.fromUriString(REFRESH_TOKEN_ENDPOINT)
                    .queryParams(queryParameters)
                    .build()
                    .toUri();
            return RequestEntity.get(uri).build();
        }
        return requestEntityConverter.convert(source);
    }
}
