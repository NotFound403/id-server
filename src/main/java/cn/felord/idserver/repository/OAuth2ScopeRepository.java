package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * The Oauth2Scope repository.
 *
 * @author felord.cn
 */
@Repository
public interface OAuth2ScopeRepository extends JpaRepository<OAuth2Scope, String> {
    @Query("select a from oauth2_scope a where a.clientId=:clientId and a.scope in :scope")
    Set<OAuth2Scope> findByClientIdAndScope(@Param("clientId") String clientId, @Param("scope") Collection<String> scope);
}
