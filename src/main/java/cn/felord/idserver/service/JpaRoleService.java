package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;

/**
 * The Jpa role service.
 * @since 1.0.0
 */
@Service
public class JpaRoleService implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Instantiates a new Jpa role service.
     *
     * @param roleRepository the role repository
     */
    public JpaRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public  Set<Role> findByNames(String clientId, Collection<String> names) {
        Assert.notEmpty(names, "names is not empty");
        return roleRepository.findByClientIdAndScope(clientId, names);
    }
}
