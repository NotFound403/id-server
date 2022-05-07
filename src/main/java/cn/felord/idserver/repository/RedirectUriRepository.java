package cn.felord.idserver.repository;

import cn.felord.idserver.entity.RedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Repository
public interface RedirectUriRepository extends JpaRepository<RedirectUri, String> {
}