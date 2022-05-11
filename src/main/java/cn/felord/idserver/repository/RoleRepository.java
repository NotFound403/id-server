package cn.felord.idserver.repository;

import cn.felord.idserver.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Find by role id role.
     *
     * @param roleId the role id
     * @return the role
     */
    @EntityGraph(attributePaths = {"permissions"})
    Role findByRoleId(String roleId);

    /**
     * Find all by role id in set.
     *
     * @param roleIds the role ids
     * @return the set
     */
    Set<Role> findAllByRoleIdIn(Collection<String> roleIds);

}