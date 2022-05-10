package cn.felord.idserver;

import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.dto.PermissionDTO;
import cn.felord.idserver.mapstruct.PermissionMapper;
import cn.felord.idserver.repository.PermissionRepository;
import cn.felord.idserver.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class RoleTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    private PermissionRepository permissionRepository;

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

    @Test
    @Transactional
    public void manyToMany() {
        Role byRoleId = roleRepository.findByRoleId("2c9460818065589b01806558ad2a0001");
        List<Permission> allByParentIdOrderBySortable = permissionRepository.findAll();
        Set<Permission> roleIdPermissions = byRoleId.getPermissions();
        System.out.println("byRoleId = " + roleIdPermissions);
        Stream<PermissionDTO> unChecked = allByParentIdOrderBySortable.stream()
                .filter((permission -> !roleIdPermissions.contains(permission)))
                .map(permissionMapper::toUnCheckedDTO);

        List<PermissionDTO> tree = Stream.concat(unChecked, roleIdPermissions.stream()
                        .map(permissionMapper::toCheckedDTO))
                .sorted(Comparator.comparingInt(PermissionDTO::getSortable))
                .collect(Collectors.toList());

        System.out.println("tree = " + tree);
    }


}
