package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;

import java.util.Collection;
import java.util.Set;

/**
 * The interface Role service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface RoleService {
    /**
     * Find by names set.
     *
     * @param clientId the client id
     * @param names    the names
     * @return the set
     */
    Set<Role> findByNames(String clientId, Collection<String> names);

}
