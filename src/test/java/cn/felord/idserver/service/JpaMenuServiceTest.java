package cn.felord.idserver.service;

import cn.felord.idserver.entity.Menu;
import lombok.SneakyThrows;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JpaMenuServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JpaMenuService jpaMenuService;

    @Test
    @Transactional
    void findByRoot() {
        final List<Menu> byRoot = this.jpaMenuService.findByRoot();
        System.out.println(byRoot);
    }

    @SneakyThrows
    @Test
    @WithUserDetails("felord")
    void menuList() {
        mockMvc.perform(MockMvcRequestBuilders.get("/system/menu/data"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void save() {
    }

    @Test
    @Transactional
    void update() {
        // Menu(id=694203021537574912, parentId=0, title=系统监控 test, type=0, openType=, icon=layui-icon  layui-icon-console, href=, children=[Menu(id=1330865171429588992, parentId=694203021537574912, title=在线用户, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon-username, href=/system/online/main, children=[Menu(id=1348562759603716096, parentId=1330865171429588992, title=在线列表, type=1, openType=_iframe, icon=layui-icon layui-icon-username, href=/system/online/data, children=[])]), Menu(id=1349016358033031168, parentId=694203021537574912, title=环境监控, type=1, openType=_iframe, icon=layui-icon layui-icon-vercode, href=/system/monitor/main, children=[]), Menu(id=442650770626711552, parentId=694203021537574912, title=定时任务, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon layui-icon  layui-icon-chat, href=/schedule/job/main, children=[Menu(id=1310390699333517312, parentId=442650770626711552, title=任务列表, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310390994826428416, parentId=442650770626711552, title=任务新增, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310391095670079488, parentId=442650770626711552, title=任务修改, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310391707069579264, parentId=442650770626711552, title=任务删除, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310397832091402240, parentId=442650770626711552, title=任务恢复, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310398020692475904, parentId=442650770626711552, title=任务停止, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310398158974484480, parentId=442650770626711552, title=任务运行, type=2, openType=, icon=layui-icon-vercode, href=, children=[])]), Menu(id=442651158935375872, parentId=694203021537574912, title=任务日志, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon  layui-icon-file, href=/schedule/log/main, children=[Menu(id=1310395250908332032, parentId=442651158935375872, title=日志列表, type=2, openType=, icon=layui-icon-vercode, href=, children=[])]), Menu(id=450300705362808832, parentId=694203021537574912, title=行为日志, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon layui-icon  layui-icon-chart, href=/system/log/main, children=[Menu(id=1310232350285627392, parentId=450300705362808832, title=操作日志, type=2, openType=, icon=layui-icon layui-icon-vercode, href=, children=[]), Menu(id=1310232462562951168, parentId=450300705362808832, title=登录日志, type=2, openType=, icon=layui-icon layui-icon-vercode, href=, children=[])])])
        //Hibernate: update menu set href=?, icon=?, open_type=?, title=?, type=? where id=?
        //Menu(id=694203021537574912, parentId=0, title=系统监控 test, type=0, openType=, icon=layui-icon  layui-icon-console, href=, children=[Menu(id=1330865171429588992, parentId=694203021537574912, title=在线用户, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon-username, href=/system/online/main, children=[Menu(id=1348562759603716096, parentId=1330865171429588992, title=在线列表, type=1, openType=_iframe, icon=layui-icon layui-icon-username, href=/system/online/data, children=[])]), Menu(id=1349016358033031168, parentId=694203021537574912, title=环境监控, type=1, openType=_iframe, icon=layui-icon layui-icon-vercode, href=/system/monitor/main, children=[]), Menu(id=442650770626711552, parentId=694203021537574912, title=定时任务, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon layui-icon  layui-icon-chat, href=/schedule/job/main, children=[Menu(id=1310390699333517312, parentId=442650770626711552, title=任务列表, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310390994826428416, parentId=442650770626711552, title=任务新增, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310391095670079488, parentId=442650770626711552, title=任务修改, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310391707069579264, parentId=442650770626711552, title=任务删除, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310397832091402240, parentId=442650770626711552, title=任务恢复, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310398020692475904, parentId=442650770626711552, title=任务停止, type=2, openType=, icon=layui-icon-vercode, href=, children=[]), Menu(id=1310398158974484480, parentId=442650770626711552, title=任务运行, type=2, openType=, icon=layui-icon-vercode, href=, children=[])]), Menu(id=442651158935375872, parentId=694203021537574912, title=任务日志, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon  layui-icon-file, href=/schedule/log/main, children=[Menu(id=1310395250908332032, parentId=442651158935375872, title=日志列表, type=2, openType=, icon=layui-icon-vercode, href=, children=[])]), Menu(id=450300705362808832, parentId=694203021537574912, title=行为日志, type=1, openType=_iframe, icon=layui-icon layui-icon layui-icon layui-icon  layui-icon-chart, href=/system/log/main, children=[Menu(id=1310232350285627392, parentId=450300705362808832, title=操作日志, type=2, openType=, icon=layui-icon layui-icon-vercode, href=, children=[]), Menu(id=1310232462562951168, parentId=450300705362808832, title=登录日志, type=2, openType=, icon=layui-icon layui-icon-vercode, href=, children=[])])])
        final Menu menu = new Menu();
        menu.setId("694203021537574912");
        menu.setTitle("系统监控 test");
        System.out.println(menu);
        this.jpaMenuService.update(menu);
        System.out.println(this.jpaMenuService.findById("694203021537574912"));
    }
}
