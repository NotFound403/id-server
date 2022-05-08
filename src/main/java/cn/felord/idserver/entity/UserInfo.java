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

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -4968368933210959171L;
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String userId;

    private String username;

    private String secret;

    private String nickName;

    private String realName;

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
}