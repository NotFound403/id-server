package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class JpaMenuServiceTest {

    @Autowired
    private JpaMenuService jpaMenuService;

    @Test
    void findByRoot() {
        final List<Menu> byRoot = this.jpaMenuService.findByRoot();
        System.out.println(byRoot);
    }

    @Test
    void save() {
    }

    @Test
    @Transactional
    void update() {
        // Hibernate: select menu0_.id as id1_3_0_, menu0_.href as href2_3_0_, menu0_.icon as icon3_3_0_, menu0_.open_type as open_typ4_3_0_, menu0_.parent_id as parent_i5_3_0_, menu0_.title as title6_3_0_, menu0_.type as type7_3_0_ from menu menu0_ where menu0_.id=?
        // Hibernate: select children0_.parent_id as parent_i5_3_1_, children0_.id as id1_3_1_, children0_.id as id1_3_0_, children0_.href as href2_3_0_, children0_.icon as icon3_3_0_, children0_.open_type as open_typ4_3_0_, children0_.parent_id as parent_i5_3_0_, children0_.title as title6_3_0_, children0_.type as type7_3_0_ from menu children0_ where children0_.parent_id=?
        // Hibernate: update menu set href=?, icon=?, open_type=?, title=?, type=? where id=?
        final Menu menu = this.jpaMenuService.findById("694203021537574912");
        menu.setTitle("系统监控 test");
        this.jpaMenuService.update(menu);
    }
}
