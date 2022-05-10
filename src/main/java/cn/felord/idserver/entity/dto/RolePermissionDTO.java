package cn.felord.idserver.entity.dto;

import lombok.Data;

import java.util.List;
/**
 * The type RolePermissionDTO.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class RolePermissionDTO {
 private String roleId;
 private List<String> permissionIds;
}
