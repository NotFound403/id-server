package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Role {
    @Id
    private String id;

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
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}