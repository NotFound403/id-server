package cn.felord.idserver.authentication.providers;

import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.Collections;

public class DelegateOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
    private final OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> delegate;

    public DelegateOAuth2AccessTokenResponseClient(OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> delegate) {
        this.delegate = delegate;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {

        String registrationId = authorizationGrantRequest.getClientRegistration().getRegistrationId();
        if (ClientProviders.WORK_WECHAT_SCAN_CLIENT.registrationId().equals(registrationId)) {
            //todo 缓存获取token 如果获取不到再请求 并放入缓存  企业微信的token不允许频繁获取
            OAuth2AccessTokenResponse tokenResponse = delegate.getTokenResponse(authorizationGrantRequest);
            String code = authorizationGrantRequest.getAuthorizationExchange()
                    .getAuthorizationResponse()
                    .getCode();
            return OAuth2AccessTokenResponse.withResponse(tokenResponse)
                    .additionalParameters(Collections.singletonMap(OAuth2ParameterNames.CODE, code))
                    .build();
        }

            return delegate.getTokenResponse(authorizationGrantRequest);

    }

}
