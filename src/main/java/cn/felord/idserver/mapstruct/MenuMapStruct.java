package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.Menu;
import org.mapstruct.*;

/**
 * 菜单结构映射
 *
 * @author zhengchalei
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface MenuMapStruct {

    /**
     * 合并内容
     *
     * @param source 源
     * @param target 目标
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "children", ignore = true)
    })
    void fireMerge(Menu source, @MappingTarget Menu target);

}
