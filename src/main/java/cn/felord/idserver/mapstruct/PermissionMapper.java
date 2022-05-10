package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.entity.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Permission结构映射
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {


    /**
     * To checked dto permission dto.
     *
     * @param permission the permission
     * @return the permission dto
     */
    @Mapping(target = "checkArr", constant = "0")
    PermissionDTO toUnCheckedDTO(Permission permission);

    /**
     * To checked dto permission dto.
     *
     * @param permission the permission
     * @return the permission dto
     */
    @Mapping(target = "checkArr", constant = "1")
    PermissionDTO toCheckedDTO(Permission permission);

}
