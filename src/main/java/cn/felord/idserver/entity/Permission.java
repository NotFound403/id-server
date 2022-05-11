package cn.felord.idserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * The type Permission.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"permission_code"})})
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Permission implements Serializable {
    private static final long serialVersionUID = -4047778726351517869L;

    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String permissionId;

    @Column(name = "parent_id", updatable = false)
    private String parentId;

    private String title;

    @Column(name = "permission_code")
    private String permissionCode;

    private String description;

    private Integer sortable;

    private Boolean enabled;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant createTime;

    @CreatedBy
    private String createId;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant updateTime;

    @LastModifiedBy
    private String updateId;

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 30)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    @ToString.Exclude
    private List<Permission> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Permission that = (Permission) o;
        return permissionId != null && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
