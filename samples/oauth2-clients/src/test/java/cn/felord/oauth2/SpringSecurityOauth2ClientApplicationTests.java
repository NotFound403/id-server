package cn.felord.oauth2;

import cn.felord.oauth2.wechat.DelegatingOAuth2UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringSecurityOauth2ClientApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> userService1 = mock(OAuth2UserService.class);
        OAuth2UserService<OAuth2UserRequest, OAuth2User> userService2 = mock(OAuth2UserService.class);

        OAuth2User mockUser1 = mock(OAuth2User.class);
        OAuth2User mockUser2 = mock(OAuth2User.class);

        OAuth2UserRequest auth2UserRequest1 = mock(OAuth2UserRequest.class);
        OAuth2UserRequest auth2UserRequest2 = mock(OAuth2UserRequest.class);

        final String registrationId1 = "idp1";
        final String registrationId2 = "idp2";

        ClientRegistration clientRegistration1 = ClientRegistration.withRegistrationId(registrationId1)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId("idp_client")
                .clientSecret("noop")
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId1}")
                .authorizationUri("https://idp1.com/authorize/oauth2")
                .tokenUri("https://idp1.com/token/oauth2")
                .userInfoUri("https://idp1.com/user/oauth2")
                .build();
        ClientRegistration clientRegistration2 = ClientRegistration.withRegistrationId(registrationId2)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientId("idp_client")
                .clientSecret("noop")
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId2}")
                .authorizationUri("https://idp2.com/authorize/oauth2")
                .tokenUri("https://idp2.com/token/oauth2")
                .userInfoUri("https://idp2.com/user/oauth2")
                .build();

        when(auth2UserRequest1.getClientRegistration()).thenReturn(clientRegistration1);
        when(userService1.loadUser(auth2UserRequest1)).thenReturn(mockUser1);

        when(auth2UserRequest2.getClientRegistration()).thenReturn(clientRegistration2);
        when(userService1.loadUser(auth2UserRequest2)).thenReturn(mockUser2);

        Map<String, OAuth2UserService<OAuth2UserRequest, OAuth2User>> userServiceMap = new HashMap<>();
           userServiceMap.put(registrationId1, userService1);
           userServiceMap.put(registrationId2, userService2);
        DelegatingOAuth2UserService<OAuth2UserRequest, OAuth2User> delegatingUserService =
                new DelegatingOAuth2UserService<>(userServiceMap);

        OAuth2User oAuth2User = delegatingUserService.loadUser(auth2UserRequest1);
        assertThat(oAuth2User).isEqualTo(mockUser1);
    }
}
