package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.ResultTable;
import cn.felord.idserver.advice.ResultTree;
import cn.felord.idserver.entity.*;
import cn.felord.idserver.repository.SysPowerRepository;
import cn.felord.idserver.service.SysPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description //todo 菜单管理控制器
 * @author rongqiu
 */

@Validated
@RestController
@RequestMapping("/system/power")
public class SysPowerController {

    private static final String MODULE_PATH = "/system/power/";

    @Autowired
    private SysPowerService sysPowerService;

    @Autowired
    private SysPowerRepository sysPowerRepository;
    /**
     * @description //todo 菜单展现页面
     * @author rongqiu
     */
    @GetMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(MODULE_PATH + "main");
        return modelAndView;
    }

    /**
     * @description //todo 菜单新增
     * @author rongqiu
     */
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }

    /**
     * @description //todo 菜单数据
     * @author rongqiu
     */
    @GetMapping("/data")
    public ResultTable data(SysPowerVO sysPowerVO ){
        List<SysPower> all = sysPowerRepository.findAll();
        return ResultTable.dataTable(all);
    }

    /**
     * @description //todo 获取父级权限选择数据
     * @author rongqiu
     */
    @GetMapping("/selectParent")
    public ResultTree selectParent(SysPower sysPower){
        List<SysPower> sysPowers = sysPowerRepository.findAll();
        SysPower power = new SysPower();
        power.setPowerName("顶级权限");
        power.setPowerId("0");
        power.setParentId("-1");
        sysPowers.add(power);
        return dataTree(sysPowers);
    }

    /**
     * @description //todo 返回tree数据
     * @author rongqiu
     */
    protected static ResultTree dataTree(Object data) {
        ResultTree resultTree = new ResultTree();
        resultTree.setData(data);
        return resultTree;
    }


    @PostMapping
    public String save(@Valid @RequestBody SysPowerVO vO) {
        return sysPowerService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        sysPowerService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody SysPowerUpdateVO vO) {
        sysPowerService.update(id, vO);
    }

    @GetMapping("/{id}")
    public SysPowerDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return sysPowerService.getById(id);
    }

    @GetMapping
    public Page<SysPowerDTO> query(@Valid SysPowerQueryVO vO) {
        return sysPowerService.query(vO);
    }
}
