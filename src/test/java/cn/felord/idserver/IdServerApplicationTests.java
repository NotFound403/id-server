package cn.felord.idserver;

import cn.felord.idserver.dto.OAuth2Client;
import cn.felord.idserver.entity.Client;
import cn.felord.idserver.repository.ClientRepository;
import cn.felord.idserver.service.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.UUID;

@SpringBootTest
class IdServerApplicationTests {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @Test
    void contextLoads() {
        Page<Client> page = clientRepository.findAll(PageRequest.of(0, 10, Sort.sort(Client.class).by(Client::getId).descending()));
        System.out.println("page = " + page.getContent());
    }

    private static RegisteredClient createRegisteredClient() {
        return RegisteredClient.withId(UUID.randomUUID().toString())
//               客户端ID和密码
                .clientId("felord")
//               此处为了避免频繁启动重复写入仓库
//                .id(id)
//                client_secret_basic    客户端需要存明文   服务器存密文
                .clientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder()
                        .encode("secret"))
//                名称 可不定义
                .clientName("felord")
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

    public static void main(String[] args) {
        OAuth2Client oAuth2Client = OAuth2Client.fromRegisteredClient(createRegisteredClient());
        System.out.println("oAuth2Client = " + oAuth2Client);
    }
}
