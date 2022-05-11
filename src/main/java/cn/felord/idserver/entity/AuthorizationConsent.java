package cn.felord.idserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type AuthorizationConsent.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Getter
@Setter
@ToString
public class AuthorizationConsent implements Serializable {
    private static final long serialVersionUID = 4969037032890824406L;
    @Id
    private String registeredClientId;
    @Id
    private String principalName;
    @Column(length = 1000)
    private String authorities;


    @Data
    public static class AuthorizationConsentId implements Serializable {
        private static final long serialVersionUID = -1813040366041244907L;
        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorizationConsent that = (AuthorizationConsent) o;
        return registeredClientId != null && Objects.equals(registeredClientId, that.registeredClientId)
                && principalName != null && Objects.equals(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredClientId, principalName);
    }
}
