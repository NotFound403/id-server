package cn.felord.idserver.service;

import cn.felord.idserver.entity.UserInfo;
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
     * Save user info.
     *
     * @param userInfo the user info
     * @return the user info
     */
    UserInfo save(UserInfo userInfo);

}
