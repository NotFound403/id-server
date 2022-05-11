package cn.felord.idserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * The type UserInfo.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraphs(
        @NamedEntityGraph(name = "userinfo.userDetails",
        attributeNodes = {
                @NamedAttributeNode(value = "authorities",subgraph = "permissions")
        },subgraphs = {
                @NamedSubgraph(name = "permissions",
                        attributeNodes = @NamedAttributeNode("permissions"))
        })
)
public class UserInfo implements UserDetails, Serializable {
    private static final long serialVersionUID = -4968368933210959171L;
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String userId;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String nickName;

    private String realName;
    @Column(unique = true,nullable = false)
    private String phoneNumber;

    private String avatarUrl;

    private String email;

    private Integer gender;

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

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    private Set<Role> authorities = Collections.emptySet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return userId != null && Objects.equals(userId, userInfo.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}