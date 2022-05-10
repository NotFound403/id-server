package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.dto.RoleDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.util.CollectionUtils;

/**
 * Role结构映射
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Mapper(componentModel = "spring",imports = {CollectionUtils.class})
public interface RoleMapper {

    /**
     * Merge.
     *
     * @param source the source
     * @param target the target
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "permissions", ignore = true)
    })
    void mergeAndIgnorePermissions(Role source, @MappingTarget Role target);

    /**
     * To checked dto permission dto.
     *
     * @param role the role
     * @return the permission dto
     */
    @Mapping(target = "parentId", constant = "1")
    @Mapping(target = "checkArr", constant = "0")
    RoleDTO toUnCheckedDTO(Role role);

    /**
     * To checked dto permission dto.
     *
     * @param role the role
     * @return the permission dto
     */
    @Mapping(target = "parentId", constant = "1")
    @Mapping(target = "checkArr", constant = "1")
    RoleDTO toCheckedDTO(Role role);
}
