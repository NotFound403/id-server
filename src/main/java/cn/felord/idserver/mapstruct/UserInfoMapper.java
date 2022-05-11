package cn.felord.idserver.mapstruct;

import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.entity.dto.UserInfoDTO;
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
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authorities", ignore = true),
    })
    void mergeIgnorePasswordAndAuthorities(UserInfo source, @MappingTarget UserInfo target);

    /**
     * Merge.
     *
     * @param source the source
     * @param target the target
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings(value = {
            @Mapping(target = "authorities", ignore = true),
    })
    void mergeIgnoreAuthorities(UserInfo source, @MappingTarget UserInfo target);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateId", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createId", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUserInfo(UserInfoDTO userInfoDTO, @MappingTarget UserInfo target);
}
