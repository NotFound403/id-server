package cn.felord.idserver.authentication.providers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

/**
 * The enum Client providers.
 */
public enum ClientProviders {
    WECHAT_WEB_LOGIN_CLIENT("wechat-web-login",
            ClientProviders::oAuth2AuthorizationRequestConsumer,
            authorizationCodeGrantRequest -> {
                ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
                HttpHeaders headers = getTokenRequestHeaders(clientRegistration);

                OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();
                MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
                // grant_type
                queryParameters.add(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
                // code
                queryParameters.add(OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
                //appid
                queryParameters.add(WechatParameterNames.APP_ID, clientRegistration.getClientId());
                //secret
                queryParameters.add(WechatParameterNames.SECRET, clientRegistration.getClientSecret());

                String tokenUri = clientRegistration.getProviderDetails().getTokenUri();

                URI uri = UriComponentsBuilder.fromUriString(tokenUri).queryParams(queryParameters).build().toUri();
                return RequestEntity.get(uri).headers(headers).build();
            }),
    /**
     * 微信网页授权.
     */
    WECHAT_WEB_CLIENT("wechat-web",
            ClientProviders::oAuth2AuthorizationRequestConsumer,
            authorizationCodeGrantRequest -> {
                ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();
                HttpHeaders headers = getTokenRequestHeaders(clientRegistration);

                OAuth2AuthorizationExchange authorizationExchange = authorizationCodeGrantRequest.getAuthorizationExchange();
                MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
                // grant_type
                queryParameters.add(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
                // code
                queryParameters.add(OAuth2ParameterNames.CODE, authorizationExchange.getAuthorizationResponse().getCode());
                // 如果有redirect-uri
                String redirectUri = authorizationExchange.getAuthorizationRequest().getRedirectUri();
                if (redirectUri != null) {
                    queryParameters.add(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
                }
                //appid
                queryParameters.add(WechatParameterNames.APP_ID, clientRegistration.getClientId());
                //secret
                queryParameters.add(WechatParameterNames.SECRET, clientRegistration.getClientSecret());

                String tokenUri = clientRegistration.getProviderDetails().getTokenUri();

                URI uri = UriComponentsBuilder.fromUriString(tokenUri).queryParams(queryParameters).build().toUri();
                return RequestEntity.get(uri).headers(headers).build();
            }),

    /**
     * The Work wechat scan client.
     * https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET
     */
    WORK_WECHAT_SCAN_CLIENT("work-wechat-scan",
            builder ->
                    builder.attributes(
                            attributes ->
                                    builder.parameters(parameters -> {
                                        LinkedHashMap<String, Object> linkedParameters = new LinkedHashMap<>();
                                        parameters.forEach((k, v) -> {
                                            if (OAuth2ParameterNames.CLIENT_ID.equals(k)) {
                                                linkedParameters.put(WechatParameterNames.APP_ID, v);
                                            }
                                            if (OAuth2ParameterNames.REDIRECT_URI.equals(k)) {
                                                linkedParameters.put(OAuth2ParameterNames.REDIRECT_URI, v);
                                            }
                                            if (OAuth2ParameterNames.STATE.equals(k)) {
                                                linkedParameters.put(OAuth2ParameterNames.STATE, v);
                                            }
                                            // 借用scope
                                            if (OAuth2ParameterNames.SCOPE.equals(k)){
                                                // 1000005
                                                linkedParameters.put(WechatParameterNames.AGENT_ID, v);
                                            }
                                        });
                                        parameters.clear();
                                        parameters.putAll(linkedParameters);
                                    })),
            authorizationCodeGrantRequest -> {
                String code = authorizationCodeGrantRequest.getAuthorizationExchange()
                        .getAuthorizationResponse()
                        .getCode();

                if (code == null) {
                    throw new OAuth2AuthenticationException("用户终止授权");
                }

                ClientRegistration clientRegistration = authorizationCodeGrantRequest.getClientRegistration();

                MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
                queryParameters.add(WechatParameterNames.CORP_ID, clientRegistration.getClientId());
                queryParameters.add(WechatParameterNames.CORP_SECRET, clientRegistration.getClientSecret());
                String tokenUri = clientRegistration.getProviderDetails().getTokenUri();
                URI uri = UriComponentsBuilder.fromUriString(tokenUri)
                        .queryParams(queryParameters)
                        .build()
                        .toUri();
                return RequestEntity.get(uri).build();
            });

    private final String registrationId;
    private final Consumer<OAuth2AuthorizationRequest.Builder> oAuth2AuthorizationRequestConsumer;
    private final Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> tokenUriConverter;

    ClientProviders(String registrationId,
                    Consumer<OAuth2AuthorizationRequest.Builder> oAuth2AuthorizationRequestConsumer,
                    Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> tokenUriConverter) {
        this.registrationId = registrationId;
        this.oAuth2AuthorizationRequestConsumer = oAuth2AuthorizationRequestConsumer;
        this.tokenUriConverter = tokenUriConverter;
    }

    /**
     * Registration id string.
     *
     * @return the string
     */
    public String registrationId() {
        return registrationId;
    }

    /**
     * Request consumer consumer.
     *
     * @return the consumer
     */
    public Consumer<OAuth2AuthorizationRequest.Builder> requestConsumer() {
        return oAuth2AuthorizationRequestConsumer;
    }

    /**
     * Converter converter.
     *
     * @return the converter
     */
    public Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> converter() {
        return tokenUriConverter;
    }


    /**
     * Gets token request headers.
     *
     * @param clientRegistration the client registration
     * @return the token request headers
     */
    static HttpHeaders getTokenRequestHeaders(ClientRegistration clientRegistration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")));
        final MediaType contentType = MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        headers.setContentType(contentType);
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.equals(clientRegistration.getClientAuthenticationMethod())
                || ClientAuthenticationMethod.BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
            String clientId = encodeClientCredential(clientRegistration.getClientId());
            String clientSecret = encodeClientCredential(clientRegistration.getClientSecret());
            headers.setBasicAuth(clientId, clientSecret);
        }
        return headers;
    }

    private static String encodeClientCredential(String clientCredential) {
        try {
            return URLEncoder.encode(clientCredential, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            // Will not happen since UTF-8 is a standard charset
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * 默认情况下Spring Security会生成授权链接：
     * {@code https://open.weixin.qq.com/connect/oauth2/authorize?response_type=code
     * &client_id=wxdf9033184b238e7f
     * &scope=snsapi_userinfo
     * &state=5NDiQTMa9ykk7SNQ5-OIJDbIy9RLaEVzv3mdlj8TjuE%3D
     * &redirect_uri=https%3A%2F%2Fmov-h5-test.felord.cn}
     * 缺少了微信协议要求的{@code #wechat_redirect}，同时 {@code client_id}应该替换为{@code app_id}
     *
     * @param builder the OAuth2AuthorizationRequest.builder
     */
    private static void oAuth2AuthorizationRequestConsumer(OAuth2AuthorizationRequest.Builder builder) {
        builder.attributes(attributes ->
                builder.parameters(parameters -> {
                    //   client_id replace into appid here
                    LinkedHashMap<String, Object> linkedParameters = new LinkedHashMap<>();
                    //  k v  must be ordered
                    parameters.forEach((k, v) -> {
                        if (OAuth2ParameterNames.CLIENT_ID.equals(k)) {
                            linkedParameters.put(WechatParameterNames.APP_ID, v);
                        } else {
                            linkedParameters.put(k, v);
                        }
                    });
                    parameters.clear();
                    parameters.putAll(linkedParameters);
                    builder.authorizationRequestUri(uriBuilder ->
                            uriBuilder.fragment(WechatParameterNames.WECHAT_REDIRECT)
                                    .build());
                }));
    }


}
