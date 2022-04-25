package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;

import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0
 */
public interface MenuService {

    List<Menu> findByRoot();
}
