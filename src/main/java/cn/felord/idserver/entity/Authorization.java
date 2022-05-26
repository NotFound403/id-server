package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * The type Authorization.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "`authorization`")
public class Authorization implements Serializable {
    private static final long serialVersionUID = 5961084257759790938L;
    @Id
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(columnDefinition="TEXT")
    private String attributes;
    private String state;
    @Column(columnDefinition="TEXT")
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    @Column(columnDefinition="TEXT")
    private String authorizationCodeMetadata;
    @Column(columnDefinition="TEXT")
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(columnDefinition="TEXT")
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(columnDefinition="TEXT")
    private String accessTokenScopes;

    @Column(columnDefinition="TEXT")
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(columnDefinition="TEXT")
    private String refreshTokenMetadata;

    @Column(columnDefinition="TEXT")
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(columnDefinition="TEXT")
    private String oidcIdTokenMetadata;
    @Column(columnDefinition="TEXT")
    private String oidcIdTokenClaims;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authorization that = (Authorization) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
