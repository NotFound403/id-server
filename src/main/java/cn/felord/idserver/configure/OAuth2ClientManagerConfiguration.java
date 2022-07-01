package cn.felord.idserver.configure;

import cn.felord.idserver.authentication.providers.DelegateMapOAuth2AccessTokenResponseConverter;
import cn.felord.idserver.authentication.providers.DelegateOAuth2RefreshTokenRequestEntityConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.DefaultRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * 兼容微信刷新token
 */
@Configuration(proxyBeanMethods = false)
public class OAuth2ClientManagerConfiguration {

    /**
     * O auth 2 authorized client manager o auth 2 authorized client manager.
     *
     * @param clientRegistrationRepository the client registration repository
     * @param authorizedClientRepository   the authorized client repository
     * @return the o auth 2 authorized client manager
     */
    @Bean
    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                OAuth2AuthorizedClientRepository authorizedClientRepository) {
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        DefaultRefreshTokenTokenResponseClient defaultRefreshTokenTokenResponseClient = new DefaultRefreshTokenTokenResponseClient();

        defaultRefreshTokenTokenResponseClient.setRequestEntityConverter(new DelegateOAuth2RefreshTokenRequestEntityConverter());
        OAuth2AccessTokenResponseHttpMessageConverter messageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        // 微信返回的content-type 是 text-plain
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
                MediaType.TEXT_PLAIN,
                new MediaType("application", "*+json")));
        // 兼容微信解析
        messageConverter.setAccessTokenResponseConverter(new DelegateMapOAuth2AccessTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(
                Arrays.asList(new FormHttpMessageConverter(),
                        messageConverter
                ));

        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        defaultRefreshTokenTokenResponseClient.setRestOperations(restTemplate);

        authorizedClientManager.setAuthorizedClientProvider(OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken((configurer) -> configurer.accessTokenResponseClient(defaultRefreshTokenTokenResponseClient))
                .clientCredentials()
                .password()
                .build());
        return authorizedClientManager;
    }
}
