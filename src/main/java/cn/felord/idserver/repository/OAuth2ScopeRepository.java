package cn.felord.idserver.repository;

import cn.felord.idserver.entity.OAuth2Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2ScopeRepository extends JpaRepository<OAuth2Scope, String> {
}