package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Client;
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
	Optional<OAuth2Client> findByClientId(String clientId);
}
