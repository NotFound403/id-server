package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * The interface O auth 2 scope repository.
 */
@Repository
public interface OAuth2ScopeRepository extends JpaRepository<OAuth2Scope, String> {

    /**
     * Find by client list.
     *
     * @param clientId the client id
     * @param scope    the scope
     * @return the list
     */
    @Query("select a from OAuth2Scope a where a.clientId=:clientId and a.scope in :scope order by a.clientId")
    List<OAuth2Scope> findByClient(@Param("clientId") String clientId, @Param("scope") Collection<String> scope);

}