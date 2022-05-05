package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.dto.OAuth2ClientDTO;
import cn.felord.idserver.repository.OAuth2ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;

/**
 * The type Jpa registered client repository.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Service
public class JpaRegisteredClientRepository implements OAuth2ClientService {
    private final OAuth2ClientRepository oAuth2ClientRepository;


    /**
     * Instantiates a new Jpa registered client repository.
     *
     * @param oAuth2ClientRepository the client repository
     */
    public JpaRegisteredClientRepository(OAuth2ClientRepository oAuth2ClientRepository) {
        Assert.notNull(oAuth2ClientRepository, "clientRepository cannot be null");
        this.oAuth2ClientRepository = oAuth2ClientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.oAuth2ClientRepository.save(OAuth2Client.fromRegisteredClient(registeredClient));
    }

    @Override
    public void saveClient(OAuth2ClientDTO client) {
        OAuth2Client oAuth2Client = client.toClient();
        oAuth2Client.setClientIdIssuedAt(Instant.now());
        this.oAuth2ClientRepository.save(oAuth2Client);
    }


    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.oAuth2ClientRepository.findById(id).map(OAuth2Client::toRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.oAuth2ClientRepository.findByClientId(clientId).map(OAuth2Client::toRegisteredClient).orElse(null);
    }

    /**
     * Page of OAuth2Client.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<OAuth2Client> page(Pageable pageable) {
        return this.oAuth2ClientRepository.findAll(pageable);
    }

    @Override
    public OAuth2Client findClientById(String id) {
        return this.oAuth2ClientRepository.searchOAuth2ClientById(id);
    }

    @Override
    public void removeByClientId(String id) {
        this.oAuth2ClientRepository.deleteById(id);
    }
}
