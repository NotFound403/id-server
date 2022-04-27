package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.exception.NotFoundException;
import cn.felord.idserver.mapstruct.MenuMapStruct;
import cn.felord.idserver.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class JpaMenuService implements MenuService {
    private final MenuRepository menuRepository;

    private final MenuMapStruct menuMapStruct;

    private static final String ROOT_ID = "0";

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> findByRoot() {
        Menu probe = new Menu();
        probe.setParentId(ROOT_ID);
        return menuRepository.findAll(Example.of(probe));
    }

    /**
     * 修改 菜单
     *
     * @param menu 菜单
     */
    @Override
    public void update(final Menu menu) {
        final Menu flush = this.menuRepository.findById(menu.getId()).orElseThrow(RuntimeException::new);
        // 此处未修改 children
        this.menuMapStruct.fireMerge(menu, flush);
        this.menuRepository.flush();
    }


    /**
     * 通过 id 查询 菜单
     *
     * @param id id
     * @return 菜单
     */
    @Override
    public Menu findById(final String id) {
        return this.menuRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
