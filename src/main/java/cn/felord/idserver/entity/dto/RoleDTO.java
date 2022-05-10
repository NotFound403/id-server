package cn.felord.idserver.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = -5383908948819333983L;
    private String roleId;
    //    private final String roleName;
    private String parentId;
    private String roleContent;
    private String checkArr = "0";
}
