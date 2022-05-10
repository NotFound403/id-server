package cn.felord.idserver.service;

import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.entity.dto.UserInfoDTO;
import cn.felord.idserver.entity.dto.UserPasswordDTO;
import cn.felord.idserver.entity.dto.UserRoleDTO;
import org.springframework.data.domain.Page;

/**
 * The interface User info service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface UserInfoService {

    /**
     * Page page.
     *
     * @param page  the page
     * @param limit the limit
     * @return the page
     */
    Page<UserInfo> page(Integer page, Integer limit);

    /**
     * Find by username user info.
     *
     * @param username the username
     * @return the user info
     */
    UserInfo findByUsername(String username);

    /**
     * Save user info.
     *
     * @param userInfoDTO the user info
     * @return the user info
     */
    UserInfo save(UserInfoDTO userInfoDTO);

    /**
     * Update.
     *
     * @param userInfo the user info
     */
    void update(UserInfo userInfo);

    /**
     * Delete by id.
     *
     * @param userId the user id
     */
    void deleteById(String userId);

    /**
     * Enable.
     *
     * @param userId the user id
     */
    void enable(String userId);

    /**
     * Find by id user info.
     *
     * @param userId the user id
     * @return the user info
     */
    UserInfo findById(String userId);

    /**
     * Change password.
     *
     * @param passwordDTO the password dto
     */
    void changePassword(UserPasswordDTO passwordDTO);

    /**
     * Bind roles.
     *
     * @param userRoleDTO the user role dto
     */
    void bindRoles(UserRoleDTO userRoleDTO);
}
