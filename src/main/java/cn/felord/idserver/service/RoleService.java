package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import org.springframework.data.domain.Page;

/**
 * The interface Role service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface RoleService {

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
