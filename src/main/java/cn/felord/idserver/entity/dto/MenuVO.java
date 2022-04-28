package cn.felord.idserver.entity.dto;

import cn.felord.idserver.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Data
public class MenuVO {

    private String id;

    private String parentId;

    private String title;

    private String type;

    private String openType;

    private String icon;

    private String href;

    private List<Menu> children;
}
