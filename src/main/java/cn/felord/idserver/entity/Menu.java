package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Menu.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
public class Menu implements Serializable {
    private static final long serialVersionUID = 4206503097158085877L;

    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String id;

    @Column(name = "parent_id", updatable = false)
    private String parentId;

    private String title;
    /**
     *  0 不可跳转   1 可跳转到 href
     */
    private String type;

    private String openType;

    private String icon;

    private String href;

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 30)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    @ToString.Exclude
    private List<Menu> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Menu menu = (Menu) o;
        return id != null && Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}