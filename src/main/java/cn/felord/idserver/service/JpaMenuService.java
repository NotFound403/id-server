package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.exception.NotFoundException;
import cn.felord.idserver.mapstruct.MenuMapper;
import cn.felord.idserver.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final MenuMapper menuMapper;

    private static final String ROOT_ID = "0";

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> findByRoot() {
        return menuRepository.findAllByParentIdOrderById(ROOT_ID);
    }

    @Override
    public List<Menu> parents() {
        Menu probe = new Menu();
        probe.setParentId("0");
        List<Menu> menus = menuRepository.findAll(Example.of(probe), Sort.sort(Menu.class)
                .by(Menu::getId)
                .ascending());
        Menu root = new Menu();
        root.setId("0");
        //dtree 需要一个 -1 的父节点
        root.setParentId("-1");
        root.setTitle("根目录");
        menus.add(root);
        return menus;
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
        this.menuMapper.fireMerge(menu, flush);
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

    @Override
    public List<Menu> findAll() {
        Sort sort = Sort.sort(Menu.class)
                .by(Menu::getId)
                .ascending();
        return this.menuRepository.findAll(sort);
    }
}
