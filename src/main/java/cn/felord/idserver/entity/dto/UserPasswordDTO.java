package cn.felord.idserver.entity.dto;

import lombok.Data;
/**
 * The type UserPasswordDTO.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class UserPasswordDTO {
    private String userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
