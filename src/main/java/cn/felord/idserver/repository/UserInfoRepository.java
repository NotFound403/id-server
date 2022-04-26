package cn.felord.idserver.repository;

import cn.felord.idserver.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User info repository.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}