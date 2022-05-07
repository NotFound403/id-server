package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Client;
import cn.felord.idserver.entity.dto.OAuth2ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * The interface Oauth2 client service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface OAuth2ClientService extends RegisteredClientRepository {

    /**
     * Save client.
     *
     * @param client the client
     */
    void saveClient(OAuth2ClientDTO client);

    /**
     * Update.
     *
     * @param client the client
     */
    void update(OAuth2ClientDTO client);

    /**
     * Page page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<OAuth2Client> page(Pageable pageable);

    /**
     * Find client by id o auth 2 client.
     *
     * @param id the id
     * @return the o auth 2 client
     */
    OAuth2Client  findClientById(String id);

    /**
     * Remove by client id.
     *
     * @param id the id
     */
    void removeByClientId(String id);
}
