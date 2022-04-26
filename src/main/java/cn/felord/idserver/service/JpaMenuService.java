package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.exception.NotFoundException;
import cn.felord.idserver.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class JpaMenuService implements MenuService {
    private final MenuRepository menuRepository;
    private static final String ROOT_ID = "0";

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> findByRoot() {
        return menuRepository.findAllByParentId(ROOT_ID);
    }

    /**
     * 修改 菜单
     *
     * @param menu 菜单
     */
    @Override
    public void update(final Menu menu) {
        final Menu flush = this.menuRepository.findById(menu.getId()).orElseThrow(RuntimeException::new);
        flush.setParentId(menu.getParentId());
        flush.setTitle(menu.getTitle());
        flush.setType(menu.getType());
        flush.setOpenType(menu.getOpenType());
        flush.setIcon(menu.getIcon());
        flush.setHref(menu.getHref());

        // 更新 Children 状态
        if (CollectionUtils.isEmpty(menu.getChildren())) {
            final Set<String> collect = menu.getChildren().stream().map(Menu::getId).collect(Collectors.toSet());
            final List<Menu> childrenFlush = this.menuRepository.findAllById(collect);
            flush.setChildren(childrenFlush);
        }
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
