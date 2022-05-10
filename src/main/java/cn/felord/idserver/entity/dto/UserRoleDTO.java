package cn.felord.idserver.entity.dto;

import lombok.Data;

import java.util.List;
/**
 * The type UserRoleDTO.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class UserRoleDTO {
    private String userId;
    private List<String> roleIds;
}
