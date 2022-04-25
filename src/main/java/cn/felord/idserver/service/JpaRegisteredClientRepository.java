package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.repository.OAuth2ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * The type Jpa registered client repository.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Service
public class JpaRegisteredClientRepository implements RegisteredClientRepository {
    private final OAuth2ClientRepository OAuth2ClientRepository;


    /**
     * Instantiates a new Jpa registered client repository.
     *
     * @param OAuth2ClientRepository the client repository
     */
    public JpaRegisteredClientRepository(OAuth2ClientRepository OAuth2ClientRepository) {
        Assert.notNull(OAuth2ClientRepository, "clientRepository cannot be null");
        this.OAuth2ClientRepository = OAuth2ClientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.OAuth2ClientRepository.save(OAuth2Client.fromRegisteredClient(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.OAuth2ClientRepository.findById(id).map(OAuth2Client::toRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.OAuth2ClientRepository.findByClientId(clientId).map(OAuth2Client::toRegisteredClient).orElse(null);
    }

    /**
     * Page of OAuth2Client.
     *
     * @param pageable the pageable
     * @return the page
     */
    public Page<OAuth2Client> page(Pageable pageable) {
        return this.OAuth2ClientRepository.findAll(pageable);
    }
}
