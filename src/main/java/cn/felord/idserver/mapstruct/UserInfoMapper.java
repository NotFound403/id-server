package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.UserInfo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * UserInfo结构映射
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    /**
     * 合并内容
     *
     * @param source 源
     * @param target 目标
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "secret", ignore = true)
    })
    void merge(UserInfo source, @MappingTarget UserInfo target);
}
