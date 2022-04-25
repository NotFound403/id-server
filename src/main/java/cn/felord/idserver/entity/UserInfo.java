package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class UserInfo {
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

    private Boolean gender;

    private Instant createTime;

    private String createId;

    private Instant updateTime;

    private String updateId;

    private Boolean enabled;

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