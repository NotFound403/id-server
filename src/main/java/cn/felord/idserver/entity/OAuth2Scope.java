package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * The Oauth2 scope.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Entity(name = "oauth2_scope")
public class OAuth2Scope {
    @Id
    private String scopeId;
    private String clientId;
    private String scope;
    public String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OAuth2Scope that = (OAuth2Scope) o;
        return scopeId != null && Objects.equals(scopeId, that.scopeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
