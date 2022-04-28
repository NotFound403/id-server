package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.Menu;
import cn.felord.idserver.entity.dto.MenuVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 菜单结构映射
 *
 * @author zhengchalei
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface MenuMapper {

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


    /**
     * 返回前端视图
     *
     * @param menu the menu
     * @return the menu vo
     */
    List<MenuVO> toVos(List<Menu> menu);


}
