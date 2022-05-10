package cn.felord.idserver.repository;

import cn.felord.idserver.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User info repository.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    /**
     * Find by username user info.
     *
     * @param username the username
     * @return the user info
     */
    Optional<UserInfo> findByUsername(String username);
}