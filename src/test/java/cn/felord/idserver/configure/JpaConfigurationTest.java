package cn.felord.idserver.configure;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpaConfigurationTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    public void test() {
        final Role role = new Role();
        role.setRoleName("test");
        role.setRoleContent("xxx");
        role.setEnabled(true);
        final Role save = this.roleRepository.save(role);
        System.out.println(save);
    }

}
