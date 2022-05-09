package cn.felord.idserver.service;

import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class JpaPermissionService implements PermissionService {
    private static final String ROOT_ID = "0";
    private final PermissionRepository permissionRepository;

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
}
