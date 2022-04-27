package cn.felord.idserver.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysPowerUpdateVO extends SysPowerVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
