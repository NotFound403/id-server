package cn.felord.idserver;

import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.OAuth2Scope;
import cn.felord.idserver.repository.OAuth2ClientRepository;
import cn.felord.idserver.service.JpaRegisteredClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@SpringBootTest
public class OAuth2ClientTests {
    @Autowired
    private JpaRegisteredClientRepository registeredClientRepository;
    @Autowired
    private OAuth2ClientRepository oAuth2ClientRepository;

    private ObjectMapper objectMapper = new ObjectMapper().registerModules(new JavaTimeModule());


    @Test

    public void addOAuth2Client() {

        RegisteredClient registeredClient = createRegisteredClient();

        OAuth2Client oAuth2Client = OAuth2Client.fromRegisteredClient(registeredClient);
        oAuth2Client.setScopes(null);
        oAuth2Client.setAuthorizationGrantTypes(null);
        oAuth2Client.setRedirectUris(null);
        oAuth2Client.setClientAuthenticationMethods(null);
        oAuth2Client.setTokenSettings(null);
        oAuth2Client.setClientSettings(null);

        oAuth2ClientRepository.save(oAuth2Client);
    }

    @Test
    @Transactional
    public void findClient() throws JsonProcessingException {
        RegisteredClient registeredClient = registeredClientRepository.findByClientId("e68e46a0-d81b-4012-8b14-266d8fb14931");

        String json = objectMapper.writeValueAsString(registeredClient);

        System.out.println("json = " + json);
    }

    @Test
    public void updateClient() {
        String clientId = "e68e46a0-d81b-4012-8b14-266d8fb14931";
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        OAuth2Client oAuth2Client = OAuth2Client.fromRegisteredClient(registeredClient);
        oAuth2Client.setClientName("felord");
        Set<OAuth2Scope> scopes = oAuth2Client.getScopes();
        OAuth2Scope oAuth2Scope = scopes.stream().findFirst().get();
        oAuth2Client.setScopes(Collections.singleton(oAuth2Scope));
        registeredClientRepository.save(oAuth2Client.toRegisteredClient());
    }


    private static RegisteredClient createRegisteredClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
//               客户端ID和密码
                .clientId(UUID.randomUUID().toString())
//               此处为了避免频繁启动重复写入仓库
//                client_secret_basic    客户端需要存明文   服务器存密文
                .clientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder()
                        .encode("secret"))
//                名称 可不定义
                .clientName("felord" + new Random().nextInt(3))
//                授权方法
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                授权类型
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                回调地址名单，不在此列将被拒绝 而且只能使用IP或者域名  不能使用 localhost
                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/felord-client-oidc")
                .redirectUri("http://127.0.0.1:8082/authorized")
                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/felord")
                .redirectUri("http://127.0.0.1:8082/foo/bar")
                .redirectUri("https://baidu.com")
//                OIDC支持
                .scope(OidcScopes.OPENID)
//                其它Scope
                .scope("message.read")
                .scope("userinfo")
                .scope("message.write")
//                JWT的配置项 包括TTL  是否复用refreshToken等等
                .tokenSettings(TokenSettings.builder().build())
//                配置客户端相关的配置项，包括验证密钥或者 是否需要授权页面
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true).build())
                .build();
    }

}
