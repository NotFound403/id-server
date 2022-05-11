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
import org.springframework.security.test.context.support.WithUserDetails;
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
    @WithUserDetails(value = "root",userDetailsServiceBeanName = "systemUserDetailsService")
    public void add(){
        Permission permission = new Permission();
        permission.setPermissionCode("user:enable");
        permission.setParentId("4028808c80b1a9c90180b1a9dcc80000");
        permission.setTitle("系统用户开关");
        permission.setDescription("系统用户开关功能权限");
        permission.setEnabled(Boolean.TRUE);
        permission.setSortable(8);
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
    @WithUserDetails(value = "felord",userDetailsServiceBeanName = "systemUserDetailsService")
    void menuList() {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/permission/data"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.code", Is.is(200)))
                .andDo(MockMvcResultHandlers.print());
    }
}
