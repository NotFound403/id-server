package cn.felord.idserver.service;

import cn.felord.idserver.entity.Permission;

import java.util.List;

/**
 * The interface Permission service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface PermissionService {

    /**
     * Find by root list.
     *
     * @return the list
     */
    List<Permission> findByRoot();

    /**
     * Save permission.
     *
     * @param permission the permission
     * @return the permission
     */
    Permission save(Permission permission);

    /**
     * Update permission.
     *
     * @param permission the permission
     * @return the permission
     */
    Permission update(Permission permission);

    /**
     * Find by id permission.
     *
     * @param permissionId the permission id
     * @return the permission
     */
    Permission findById(String permissionId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Permission> findAll();

    /**
     * Parents list.
     *
     * @return the list
     */
    List<Permission> parents();
}
