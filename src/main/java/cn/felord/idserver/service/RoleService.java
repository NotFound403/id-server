package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
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

    /**
     * Find by client set.
     *
     * @param clientId the client id
     * @return the set
     */
    List<Role> findByClient(String clientId);

   /**
    * Save role.
    *
    * @param role the role
    * @return the role
    */
   Role save(Role role);

   /**
    * Page page.
    *
    * @param page  the page
    * @param limit the limit
    * @return the page
    */
   Page<Role> page(Integer page, Integer limit);

}
