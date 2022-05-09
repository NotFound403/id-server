package cn.felord.idserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * The type Role.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"role_name"})})
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Role implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = -6963523161322981431L;
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    private String roleContent;

    private String description;

    private Boolean enabled;

    @CreatedDate
    private Instant createTime;

    @CreatedBy
    private String createId;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant updateTime;

    @LastModifiedBy
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateId;

    @ManyToMany(targetEntity = Permission.class, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    @ToString.Exclude
    private Set<Permission> permissions = Collections.emptySet();


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

    @Override
    public String getAuthority() {
        return "ROLE_"+this.roleName;
    }
}
