package cn.felord.idserver.service;

import cn.felord.idserver.entity.OAuth2Scope;

import java.util.Set;

/**
 * The Oauth2 service.
 */
public interface OAuth2Service {
    /**
     * Find By Names.
     *
     * @param names the names
     * @return the set
     */
    Set<OAuth2Scope> findByNames(Set<String> names);
}
