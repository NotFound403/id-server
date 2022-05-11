package cn.felord.idserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_role")
@IdClass(UserRole.UserRoleId.class)
@Getter
@Setter
@ToString
public class UserRole {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Id
    @Column(name = "role_id", nullable = false)
    private String roleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    @ToString.Exclude
    private UserInfo user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false,insertable = false,updatable = false)
    @ToString.Exclude
    private Role role;

    @Data
    public static class UserRoleId implements Serializable {
        private static final long serialVersionUID = 2687087097095274803L;
        private String userId;

        private String roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRole userRole = (UserRole) o;
        return userId != null && Objects.equals(userId, userRole.userId)
                && roleId != null && Objects.equals(roleId, userRole.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}