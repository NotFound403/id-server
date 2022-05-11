package cn.felord.idserver.repository;

import cn.felord.idserver.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User role repository.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    /**
     * Count by role id long.
     *
     * @param roleId the role id
     * @return the long
     */
    long countByRoleId(String roleId);
}