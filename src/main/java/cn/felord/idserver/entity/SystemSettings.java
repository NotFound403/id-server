package cn.felord.idserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The System settings.
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Entity
@Getter
@Setter
@ToString
public class SystemSettings {
    @Id
    @GenericGenerator(name = "uuid-hex", strategy = "uuid.hex")
    @GeneratedValue(generator = "uuid-hex")
    private String id;
    private String systemName;
    private String copyright;
}
