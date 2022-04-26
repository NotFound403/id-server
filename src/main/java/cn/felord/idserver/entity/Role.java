package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
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
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String roleId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "role_name")
    private String roleName;

    private String roleContent;

    private Boolean enabled;

    @CreatedDate
    private Instant createTime;

    @CreatedBy
    private String createId;

    @LastModifiedDate
    private Instant updateTime;

    @LastModifiedBy
    private String updateId;

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
