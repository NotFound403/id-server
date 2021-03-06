package cn.felord.idserver;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.service.JpaMenuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@SpringBootTest
public class MenuTests {
  @Autowired
  private JpaMenuService jpaMenuService;
    @Test
    @Transactional
    public void add(){
        String parentId="694203021537574913";

        Menu menu = new Menu();
            menu.setParentId(parentId);
            menu.setTitle("菜单管理");
            menu.setType("1");
            menu.setOpenType("_iframe");
            menu.setIcon("icon pear-icon pear-icon-menu");
            menu.setHref("/system/menu/main");
        Menu save = jpaMenuService.save(menu);
        Assertions.assertNotNull(save.getId());

    }
}
