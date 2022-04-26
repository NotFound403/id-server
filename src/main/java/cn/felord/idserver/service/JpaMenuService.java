package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class JpaMenuService implements MenuService {
    private final MenuRepository menuRepository;
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
}
