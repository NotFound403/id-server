package cn.felord.idserver;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.service.JpaMenuService;
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
            menu.setTitle("客户端列表");
            menu.setType("1");
            menu.setOpenType("_iframe");
            menu.setIcon("layui-icon-rate");
            menu.setHref("/system/client/main");
        Menu save = jpaMenuService.save(menu);

        System.out.println("save = " + save);

    }
}
