package cn.felord.idserver.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * The type PermissionDTO.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = -2483491781009497731L;
    private String permissionId;
    private String parentId;
    private String title;
    private Integer sortable;
    private String checkArr = "0";
}
