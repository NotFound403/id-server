package cn.felord.idserver;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.repository.MenuRepository;
import cn.felord.idserver.repository.OAuth2ClientRepository;
import cn.felord.idserver.service.OAuth2ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class IdServerApplicationTests {
    @Autowired
    OAuth2ClientRepository OAuth2ClientRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    OAuth2ClientService jpaRegisteredClientRepository;

    @Test
    void contextLoads() throws JsonProcessingException {

        Page<OAuth2Client> page = jpaRegisteredClientRepository.page(PageRequest.of(0, 10, Sort.sort(OAuth2Client.class)
                .by(OAuth2Client::getClientIdIssuedAt).descending()));

    }

    @Test
    public void menu() throws JsonProcessingException {

        Menu probe = new Menu();
        probe.setParentId("0");
        List<Menu> all = menuRepository.findAll(Example.of(probe));


        String s = new ObjectMapper().writeValueAsString(all);
        System.out.println("s = " + s);

    }



    private static RegisteredClient createRegisteredClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
//               ?????????ID?????????
                .clientId(UUID.randomUUID().toString())
//               ????????????????????????????????????????????????
//                .id(id)
//                client_secret_basic    ????????????????????????   ??????????????????
                .clientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder()
                        .encode("secret"))
//                ?????? ????????????
                .clientName("felord"+ new Random().nextInt(3))
//                ????????????
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                ????????????
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                ????????????????????????????????????????????? ??????????????????IP????????????  ???????????? localhost
                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/felord-client-oidc")
                .redirectUri("http://127.0.0.1:8082/authorized")
                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/felord")
                .redirectUri("http://127.0.0.1:8082/foo/bar")
                .redirectUri("https://baidu.com")
//                OIDC??????
                .scope(OidcScopes.OPENID)
//                ??????Scope
                .scope("message.read")
                .scope("userinfo")
                .scope("message.write")
//                JWT???????????? ??????TTL  ????????????refreshToken??????
                .tokenSettings(TokenSettings.builder().build())
//                ???????????????????????????????????????????????????????????? ????????????????????????
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true).build())
                .build();
    }
}
