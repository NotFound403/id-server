package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.entity.dto.MenuVO;

import java.util.List;

/**
 * The interface Menu service.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public interface MenuService {


    /**
     * Save menu.
     *
     * @param menu the menu
     * @return the menu
     */
    Menu save(Menu menu);

    /**
     * 修改 菜单
     *
     * @param menu 菜单
     */
    void update(Menu menu);

    /**
     * 通过 id 查询 菜单
     *
     * @param id id
     * @return 菜单
     */
    Menu findById(String id);

    /**
     * Find by root list.
     *
     * @return the list
     */
    List<MenuVO> findByRoot();
}
