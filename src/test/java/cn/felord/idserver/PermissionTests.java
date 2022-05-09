package cn.felord.idserver;

import cn.felord.idserver.entity.Permission;
import cn.felord.idserver.service.PermissionService;
import lombok.SneakyThrows;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PermissionTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private PermissionService permissionService;

    @Test
    @WithAnonymousUser
   public void add(){
        Permission permission = new Permission();
        permission.setPermissionCode("client:remove");
        permission.setParentId("2c9c208180a78df00180a78e011e0000");
        permission.setTitle("客户端删除");
        permission.setDescription("客户端删除功能权限");
        permission.setEnabled(Boolean.TRUE);
        permission.setSortable(4);
        permissionService.save(permission);
   }

   @Test
   @Transactional
   public void findAllByRoot(){
       List<Permission> byRoot = permissionService.findByRoot();
       System.out.println("byRoot = " + byRoot);
   }

    @SneakyThrows
    @Test
    @WithAnonymousUser
    void menuList() {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/permission/data"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.code", Is.is(200)))
                .andDo(MockMvcResultHandlers.print());
    }
}
