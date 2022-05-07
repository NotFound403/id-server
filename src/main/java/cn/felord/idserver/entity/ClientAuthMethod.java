package cn.felord.idserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author felord.cn
 * @see ClientAuthenticationMethod
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@IdClass(ClientAuthMethod.ClientAuthenticationMethodId.class)
@Table(name = "client_auth_method")
public class ClientAuthMethod implements Serializable {
    private static final long serialVersionUID = 8357713071222963428L;
    @Id
    @Column(name = "client_id", insertable = false, updatable = false)
    private String clientId;
    @Id
    private String clientAuthenticationMethod;

    @Data
    public static class ClientAuthenticationMethodId implements Serializable {
        private static final long serialVersionUID = 6996307333048077105L;
        private String clientId;

        private String clientAuthenticationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClientAuthMethod that = (ClientAuthMethod) o;
        return clientId != null && Objects.equals(clientId, that.clientId)
                && clientAuthenticationMethod != null && Objects.equals(clientAuthenticationMethod, that.clientAuthenticationMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientAuthenticationMethod);
    }

    public ClientAuthenticationMethod toAuthenticationMethod() {
        return new ClientAuthenticationMethod(this.clientAuthenticationMethod);
    }

}
