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
@IdClass(RedirectUri.RedirectUriId.class)
@Table(name = "redirect_uri")
public class RedirectUri  implements Serializable{
    private static final long serialVersionUID = 9221146008979764156L;
    @Id
    @Column(name="client_id",insertable = false,updatable = false)
    private String clientId;
    @Id
    private String redirectUri;

    @Data
    public static class RedirectUriId implements Serializable {
        private static final long serialVersionUID = -8081989338438799123L;
        private String clientId;
        private String redirectUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RedirectUri that = (RedirectUri) o;
        return clientId != null && Objects.equals(clientId, that.clientId)
                && redirectUri != null && Objects.equals(redirectUri, that.redirectUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, redirectUri);
    }
}
