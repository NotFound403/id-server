package cn.felord.idserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@IdClass(OAuth2GrantType.GrantTypeId.class)
@Table(name = "oauth2_grant_type")
public class OAuth2GrantType implements Serializable {
    private static final long serialVersionUID = -6157485899335872648L;
    @Id
    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientId;
    @Id
    private String grantTypeName;

    @Data
    public static class GrantTypeId implements Serializable {
        private static final long serialVersionUID = 4877568519791270151L;
        private String clientId;
        private String grantTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OAuth2GrantType that = (OAuth2GrantType) o;
        return clientId != null && Objects.equals(clientId, that.clientId)
                && grantTypeName != null && Objects.equals(grantTypeName, that.grantTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, grantTypeName);
    }

    public AuthorizationGrantType toGrantType() {
        return new AuthorizationGrantType(grantTypeName);
    }

}
