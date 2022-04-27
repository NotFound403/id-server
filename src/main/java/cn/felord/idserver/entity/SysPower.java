package cn.felord.idserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_power")
public class SysPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限编号
     */
    @Id
    @Column(name = "power_id", nullable = false)
    private String powerId;

    /**
     * 权限名称
     */
    @Column(name = "power_name")
    private String powerName;

    /**
     * 权限类型
     */
    @Column(name = "power_type")
    private String powerType;

    /**
     * 权限标识
     */
    @Column(name = "power_code")
    private String powerCode;

    /**
     * 权限路径
     */
    @Column(name = "power_url")
    private String powerUrl;

    /**
     * 打开方式
     */
    @Column(name = "open_type")
    private String openType;

    /**
     * 父类编号
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否开启
     */
    @Column(name = "enable")
    private String enable;

    /**
     * 是否删除0否1是
     */
    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;

}
