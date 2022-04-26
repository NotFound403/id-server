package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Repository
public interface OAuth2ClientRepository extends JpaRepository<OAuth2Client, String> {
	Optional<OAuth2Client> findByClientId(String clientId);
}
