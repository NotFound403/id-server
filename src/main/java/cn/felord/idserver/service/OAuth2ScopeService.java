package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Scope;

import java.util.Collection;
import java.util.List;

/**
 * The interface Oauth2 scope service.
 */
public interface OAuth2ScopeService {

    /**
     * Find by client id and scope list.
     *
     * @param clientId the client id
     * @param scope    the scope
     * @return the list
     */
    List<OAuth2Scope> findByClientIdAndScope(String clientId, Collection<String> scope);

}
