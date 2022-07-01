package cn.felord.idserver.authentication.providers;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The type O auth 2 provider configurer.
 */
public class OAuth2ProviderConfigurer extends AbstractHttpConfigurer<OAuth2ProviderConfigurer, HttpSecurity> {

    private final DelegateClientRegistrationRepository delegateClientRegistrationRepository;

    private Consumer<OAuth2LoginConfigurer<HttpSecurity>> oAuth2LoginConfigurerConsumer = oAuth2ProviderConfigurer -> {
    };


    /**
     * Instantiates a new O auth 2 provider configurer.
     *
     * @param delegateClientRegistrationRepository the delegate client registration repository
     */
    public OAuth2ProviderConfigurer(DelegateClientRegistrationRepository delegateClientRegistrationRepository) {
        this.delegateClientRegistrationRepository = delegateClientRegistrationRepository;
    }

    /**
     * Wechat webclient o auth 2 provider configurer.
     *
     * @param appId  the app id
     * @param secret the secret
     * @return the o auth 2 provider configurer
     */
    public OAuth2ProviderConfigurer wechatWebclient(String appId, String secret) {
        ClientRegistration clientRegistration = getBuilder(ClientProviders.WECHAT_WEB_CLIENT.registrationId(), ClientAuthenticationMethod.NONE)
                .clientId(appId)
                .clientSecret(secret)
                .scope("snsapi_userinfo")
                .authorizationUri("https://open.weixin.qq.com/connect/oauth2/authorize")
                .tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token")
                .userInfoUri("https://api.weixin.qq.com/sns/userinfo")
                .clientName("微信网页授权")
                .build();
        this.delegateClientRegistrationRepository.addClientRegistration(clientRegistration);
        return this;
    }

    /**
     * Wechat web loginclient o auth 2 provider configurer.
     *
     * @param appId  the app id
     * @param secret the secret
     * @return the o auth 2 provider configurer
     */
    public OAuth2ProviderConfigurer wechatWebLoginclient(String appId, String secret) {
        ClientRegistration clientRegistration = getBuilder(ClientProviders.WECHAT_WEB_LOGIN_CLIENT.registrationId(), ClientAuthenticationMethod.NONE)
                .clientId(appId)
                .clientSecret(secret)
                .scope("snsapi_login")
                .authorizationUri("https://open.weixin.qq.com/connect/qrconnect")
                .tokenUri("https://api.weixin.qq.com/sns/oauth2/access_token")
                .userInfoUri("https://api.weixin.qq.com/sns/userinfo")
                .clientName("微信")
                .build();
        this.delegateClientRegistrationRepository.addClientRegistration(clientRegistration);
        return this;
    }

    /**
     * Work wechat web loginclient o auth 2 provider configurer.
     *
     * @param corpId  the corp id
     * @param secret  the secret
     * @param agentId the agent id
     * @return the o auth 2 provider configurer
     */
    public OAuth2ProviderConfigurer workWechatWebLoginclient(String corpId, String secret, String agentId) {
        ClientRegistration clientRegistration = getBuilder(ClientProviders.WORK_WECHAT_SCAN_CLIENT.registrationId(), ClientAuthenticationMethod.NONE)
                .clientId(corpId)
                .clientSecret(secret)
                .scope(agentId)
                .authorizationUri("https://open.work.weixin.qq.com/wwopen/sso/qrConnect")
                .tokenUri("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .userInfoUri("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo")
                .clientName("企业微信")
                .build();
        this.delegateClientRegistrationRepository.addClientRegistration(clientRegistration);
        return this;
    }

    /**
     * O auth 2 login configurer consumer o auth 2 provider configurer.
     *
     * @param oAuth2LoginConfigurerConsumer the o auth 2 login configurer consumer
     * @return the o auth 2 provider configurer
     */
    public OAuth2ProviderConfigurer oAuth2LoginConfigurerConsumer(Consumer<OAuth2LoginConfigurer<HttpSecurity>> oAuth2LoginConfigurerConsumer) {
        this.oAuth2LoginConfigurerConsumer = oAuth2LoginConfigurerConsumer;
        return this;
    }

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}");
        return builder;
    }

    @Override
    public void init(HttpSecurity httpSecurity) throws Exception {

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        // 微信返回的content-type 是 text-plain
        tokenResponseHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, new MediaType("application", "*+json")));
        // 兼容微信解析
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new DelegateMapOAuth2AccessTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());


        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRequestEntityConverter(new OAuth2ProviderAuthorizationCodeGrantRequestEntityConverter());
        tokenResponseClient.setRestOperations(restTemplate);


        WechatOAuth2UserService value = new WechatOAuth2UserService();
        Map<String, OAuth2UserService<OAuth2UserRequest, OAuth2User>> oAuth2UserServiceMap = new HashMap<>();
        oAuth2UserServiceMap.put(ClientProviders.WECHAT_WEB_CLIENT.registrationId(), value);
        oAuth2UserServiceMap.put(ClientProviders.WECHAT_WEB_LOGIN_CLIENT.registrationId(), value);
        oAuth2UserServiceMap.put(ClientProviders.WORK_WECHAT_SCAN_CLIENT.registrationId(), new WorkWechatOAuth2UserService());

        httpSecurity.setSharedObject(ClientRegistrationRepository.class, delegateClientRegistrationRepository);


        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(delegateClientRegistrationRepository, OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizer::customize);

        OAuth2LoginConfigurer<HttpSecurity> httpSecurityOAuth2LoginConfigurer = httpSecurity.oauth2Login();

        httpSecurityOAuth2LoginConfigurer.authorizationEndpoint()
                // 授权端点配置
                .authorizationRequestResolver(resolver).and()
                // 获取token端点配置  比如根据code 获取 token
                .tokenEndpoint().accessTokenResponseClient(new DelegateOAuth2AccessTokenResponseClient(tokenResponseClient))
                .and()
                // 获取用户信息端点配置  根据accessToken获取用户基本信息
                .userInfoEndpoint().userService(new DelegatingOAuth2UserService<>(oAuth2UserServiceMap));
        this.oAuth2LoginConfigurerConsumer.accept(httpSecurityOAuth2LoginConfigurer);

    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = httpSecurity
                .getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null) {
            Map<String, String> loginUrlToClientName = new HashMap<>();
            delegateClientRegistrationRepository.getClientRegistrationMap().forEach((s, v) -> {
                String authorizationRequestUri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + v.getRegistrationId();
                loginUrlToClientName.put(authorizationRequestUri, v.getClientName());
            });
            loginPageGeneratingFilter.setOauth2AuthenticationUrlToClientName(loginUrlToClientName);
        }
    }
}
