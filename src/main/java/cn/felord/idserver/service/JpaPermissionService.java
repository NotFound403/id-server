package cn.felord.idserver.service;

import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.dto.PermissionDTO;
import cn.felord.idserver.mapstruct.PermissionMapper;
import cn.felord.idserver.repository.PermissionRepository;
import cn.felord.idserver.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class JpaPermissionService implements PermissionService {
    private static final String ROOT_ID = "0";
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public List<Permission> findByRoot() {
        return permissionRepository.findAllByParentIdOrderBySortable(ROOT_ID);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        return null;
    }

    @Override
    public Permission findById(String permissionId) {
        return permissionRepository.findById(permissionId).orElse(null);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll(Sort.sort(Permission.class)
                .by(Permission::getSortable));
    }

    @Override
    public List<Permission> parents() {
        Permission probe = new Permission();
        probe.setParentId("0");
        List<Permission> permissions = permissionRepository.findAll(Example.of(probe), Sort.sort(Permission.class)
                .by(Permission::getSortable)
                .ascending());
        Permission root = new Permission();
        root.setPermissionId("0");
        //dtree 需要一个 -1 的父节点
        root.setParentId("-1");
        root.setPermissionCode("");
        root.setTitle("根目录");
        permissions.add(root);
        return permissions;
    }

    @Override
    public List<PermissionDTO> permissionTreeData(String roleId) {
        Role role = roleRepository.findByRoleId(roleId);
        if (role==null){
            return Collections.emptyList();
        }
        Set<Permission> roleIdPermissions = role.getPermissions();
        List<Permission> all  = permissionRepository.findAll();
        Stream<PermissionDTO> unChecked = all.stream()
                .filter((permission -> !roleIdPermissions.contains(permission)))
                .map(permissionMapper::toUnCheckedDTO);
        return Stream.concat(unChecked, roleIdPermissions.stream().map(permissionMapper::toCheckedDTO))
                .sorted(Comparator.comparingInt(PermissionDTO::getSortable)).collect(Collectors.toList());
    }
}
