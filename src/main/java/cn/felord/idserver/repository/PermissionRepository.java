package cn.felord.idserver.repository;

import cn.felord.idserver.entity.Permission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * The interface Permission repository.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    /**
     * Find all by parent id order by sortable list.
     *
     * @param parentId the parent id
     * @return the list
     */
    @EntityGraph(attributePaths = {"children"})
    List<Permission> findAllByParentIdOrderBySortable(String parentId);

    /**
     * Find all by permission id in list.
     *
     * @param permissionIds the permission ids
     * @return the list
     */
    Set<Permission> findAllByPermissionIdIn(Collection<String> permissionIds);
}