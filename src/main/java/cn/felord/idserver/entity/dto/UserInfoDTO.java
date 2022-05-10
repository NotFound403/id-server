package cn.felord.idserver.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = -7240227419322938178L;
    private String username;
    private String password;
    private String nickName;
    private String realName;
    private String phoneNumber;
    private String avatarUrl;
    private String email;
    private Integer gender;
}
