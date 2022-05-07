package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.enumate.Enabled;
import cn.felord.idserver.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The Jpa role service.
 *
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class JpaRoleService implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        role.setEnabled(Boolean.valueOf(Enabled.ENABLE.val()));
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> page(Integer page, Integer limit) {
        page = Math.max(page - 1, 0);
        return roleRepository.findAll(PageRequest.of(page, limit, Sort.sort(Role.class)
                .by(Role::getCreateTime).descending()));
    }

}
