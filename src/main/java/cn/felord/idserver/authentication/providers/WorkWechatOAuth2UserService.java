package cn.felord.idserver.authentication.providers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;


/**
 * 获取微信用户信息的服务接口
 *
 * @author felord.cn
 * @since 2021 /8/12 17:45
 */
public class WorkWechatOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";
    private static final String MISSING_CODE_ERROR = "missing_code_attribute";
    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";
    private static final ParameterizedTypeReference<WorkWechatOAuth2User> OAUTH2_USER_OBJECT = new ParameterizedTypeReference<WorkWechatOAuth2User>() {
    };
    private final RestOperations restOperations;

    /**
     * Instantiates a new Wechat o auth 2 user service.
     */
    public WorkWechatOAuth2UserService() {
        RestTemplate restTemplate = new RestTemplate(Collections.singletonList(new WechatOAuth2UserHttpMessageConverter()));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public WorkWechatOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String registrationId = clientRegistration.getRegistrationId();
        Assert.notNull(userRequest, "userRequest cannot be null");
        if (!StringUtils
                .hasText(clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri())) {
            OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_INFO_URI_ERROR_CODE,
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: "
                            + registrationId,
                    null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
        String code = (String) userRequest.getAdditionalParameters().get(OAuth2ParameterNames.CODE);
        if (!StringUtils.hasText(code)) {
            OAuth2Error oauth2Error = new OAuth2Error(MISSING_CODE_ERROR,
                    "Missing required \"code\" attribute in UserInfoEndpoint for Client Registration: "
                            + registrationId,
                    null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
        return getResponse(userRequest).getBody();
    }

    /**
     * 获取微信用户信息借鉴{@link OAuth2AccessTokenResponseClient}
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param userRequest the user request
     * @return response
     */
    private ResponseEntity<WorkWechatOAuth2User> getResponse(OAuth2UserRequest userRequest) {
        String userInfoUri = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();
        try {
            LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

            queryParams.add(OAuth2ParameterNames.ACCESS_TOKEN, userRequest.getAccessToken().getTokenValue());
            queryParams.add(OAuth2ParameterNames.CODE, String.valueOf(userRequest.getAdditionalParameters().get(OAuth2ParameterNames.CODE)));

            URI userInfoEndpoint = UriComponentsBuilder.fromUriString(userInfoUri).queryParams(queryParams).build().toUri();

            return this.restOperations.exchange(userInfoEndpoint, HttpMethod.GET, null, OAUTH2_USER_OBJECT);

        } catch (OAuth2AuthorizationException ex) {
            OAuth2Error oauth2Error = ex.getError();
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("Error details: [");
            errorDetails.append("UserInfo Uri: ")
                    .append(userInfoUri);
            errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
            if (oauth2Error.getDescription() != null) {
                errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
            }
            errorDetails.append("]");
            oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails,
                    null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (UnknownContentTypeException ex) {
            String errorMessage = "An error occurred while attempting to retrieve the UserInfo Resource from '"
                    + userInfoUri
                    + "': response contains invalid content type '" + ex.getContentType() + "'. "
                    + "The UserInfo Response should return a JSON object (content type 'application/json') "
                    + "that contains a collection of name and value pairs of the claims about the authenticated End-User. "
                    + "Please ensure the UserInfo Uri in UserInfoEndpoint for Client Registration '"
                    + userRequest.getClientRegistration().getRegistrationId() + "' conforms to the UserInfo Endpoint, "
                    + "as defined in OpenID Connect 1.0: 'https://openid.net/specs/openid-connect-core-1_0.html#UserInfo'";
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, errorMessage, null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }
    }


    private static class WechatOAuth2UserHttpMessageConverter
            extends AbstractHttpMessageConverter<WorkWechatOAuth2User> {

        private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


        private final GenericHttpMessageConverter<Object> jsonMessageConverter = HttpMessageConverters.getJsonMessageConverter();


        public WechatOAuth2UserHttpMessageConverter() {
            super(DEFAULT_CHARSET, MediaType.TEXT_PLAIN,
                    MediaType.APPLICATION_JSON,
                    new MediaType("application", "*+json"));
        }

        @Override
        protected boolean supports(Class<?> clazz) {
            return WorkWechatOAuth2User.class.isAssignableFrom(clazz);
        }

        @Override
        protected WorkWechatOAuth2User readInternal(Class<? extends WorkWechatOAuth2User> clazz,
                                                HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
            try {
                // gh-6463: Parse parameter values as Object in order to handle potential JSON
                // Object and then convert values to String
                return (WorkWechatOAuth2User) this.jsonMessageConverter
                        .read(OAUTH2_USER_OBJECT.getType(), null, inputMessage);

            } catch (Exception ex) {
                throw new HttpMessageNotReadableException(
                        "An error occurred reading the OAuth 2.0 Access Token Response: " + ex.getMessage(), ex,
                        inputMessage);
            }
        }

        @Override
        protected void writeInternal(WorkWechatOAuth2User tokenResponse, HttpOutputMessage outputMessage)
                throws HttpMessageNotWritableException {
            // noop
        }


        static class HttpMessageConverters {

            private static final boolean jackson2Present;

            private static final boolean gsonPresent;

            private static final boolean jsonbPresent;

            static {
                ClassLoader classLoader = HttpMessageConverters.class.getClassLoader();
                jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)
                        && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
                gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
                jsonbPresent = ClassUtils.isPresent("javax.json.bind.Jsonb", classLoader);
            }

            private HttpMessageConverters() {
            }

            static GenericHttpMessageConverter<Object> getJsonMessageConverter() {
                if (jackson2Present) {
                    return new MappingJackson2HttpMessageConverter();
                }
                if (gsonPresent) {
                    return new GsonHttpMessageConverter();
                }
                if (jsonbPresent) {
                    return new JsonbHttpMessageConverter();
                }
                return null;
            }

        }
    }
}
