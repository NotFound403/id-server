package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.Objects;

/**
 * The type Role.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "role_name"})})
@Getter
@Setter
@ToString
public class Role {
    @Id
    private String roleId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "role_name")
    private String roleName;

    private String roleContent;

    private Instant createTime;

    private String createId;

    private Instant updateTime;

    private String updateId;

    private Boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return roleId != null && Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}