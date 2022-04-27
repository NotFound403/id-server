package cn.felord.idserver;

import cn.felord.idserver.entity.UserInfo;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@SpringBootTest
public class UserInfoTests {

    @Test
    public void mapstructTest() {
        UserInfo userInfo = new UserInfo();
        userInfo.setRealName("tom");
        userInfo.setUsername("username");


        UserDTO userDTO = new UserDTO();
        userDTO.setRealName("cat");


        UserMapper mapper = UserMapper.MAPPER;

        mapper.update(userDTO,userInfo);


        Assertions.assertEquals("cat", userInfo.getRealName());
        Assertions.assertNotNull(userInfo.getUsername());

    }


    @Data
    static class UserDTO {

        private String username;

        private String secret;

        private String nickName;

        private String realName;

        private String phoneNumber;
    }


    @Mapper
    public static interface UserMapper {
        UserMapper MAPPER = Mappers.getMapper(UserMapper.class);


        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void update(UserDTO dto, @MappingTarget UserInfo userInfo);
    }


}
