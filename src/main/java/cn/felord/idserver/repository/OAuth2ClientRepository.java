package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Oauth2 client repository.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Repository
public interface OAuth2ClientRepository extends JpaRepository<OAuth2Client, String> {
    /**
     * Find by client id optional.
     *
     * @param clientId the client id
     * @return the optional
     */
    @EntityGraph(attributePaths = {"clientAuthenticationMethods",
            "authorizationGrantTypes",
            "redirectUris",
            "scopes",
            "clientSettings",
            "tokenSettings"})
    Optional<OAuth2Client> findByClientId(String clientId);

    /**
     * Find client by id o auth 2 client.
     *
     * @param id the id
     * @return the o auth 2 client
     */
    @EntityGraph(attributePaths = {"clientAuthenticationMethods",
            "authorizationGrantTypes",
            "redirectUris",
            "scopes",
            "clientSettings",
            "tokenSettings"})
    OAuth2Client searchOAuth2ClientById(String id);
}
