package cn.felord.idserver.repository;

import cn.felord.idserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("select a from Role a where a.clientId=:clientId and a.roleName in :scope")
    Set<Role> findByClientIdAndScope(@Param("clientId") String clientId, @Param("scope") Collection<String> scope);
}