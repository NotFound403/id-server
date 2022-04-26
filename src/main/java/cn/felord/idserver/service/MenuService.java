package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;

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
     * Find by root list.
     *
     * @return the list
     */
    List<Menu> findByRoot();
}
