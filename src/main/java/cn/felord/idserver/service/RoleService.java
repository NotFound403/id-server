package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.dto.RoleDTO;
import cn.felord.idserver.entity.dto.RolePermissionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

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
    * Update role.
    *
    * @param role the role
    * @return the role
    */
   Role update(Role role);

   /**
    * Find by role id role.
    *
    * @param roleId the role id
    * @return the role
    */
   Role findByRoleId(String roleId);

   /**
    * Delete by id.
    *
    * @param roleId the role id
    */
   void deleteById(String roleId);

   /**
    * Bind permissions.
    *
    * @param rolePermissionDTO the role permission dto
    */
   void bindPermissions(RolePermissionDTO rolePermissionDTO);

   /**
    * Page page.
    *
    * @param page  the page
    * @param limit the limit
    * @return the page
    */
   Page<Role> page(Integer page, Integer limit);

   /**
    * role tree data list.
    *
    * @param userId the user id
    * @return the list
    */
   List<RoleDTO> roleTreeData(String userId);
}
