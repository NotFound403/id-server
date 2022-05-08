package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.advice.Rest;
import cn.felord.idserver.advice.RestBody;
import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.entity.dto.UserPasswordDTO;
import cn.felord.idserver.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 分为系统用户和普通用户
 *
 * @author felord.cn
 * @since 1.0.0
 */
@Controller
@AllArgsConstructor
public class UserController extends BaseController {
    private UserInfoService userInfoService;

    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/user/main")
    public String main() {
        return "system/user/main";
    }

    /**
     * Page page.
     *
     * @param page  the page
     * @param limit the limit
     * @return the page
     */
    @GetMapping("/system/user/data")
    @ResponseBody
    public Page<UserInfo> page(@RequestParam Integer page, @RequestParam Integer limit) {
        return userInfoService.page(page, limit);
    }

    /**
     * Add string.
     *
     * @return the string
     */
    @GetMapping("/system/user/add")
    public String add() {
        return "system/user/add";
    }

    /**
     * Add rest.
     *
     * @param userInfo the user info
     * @return the rest
     */
    @PostMapping("/system/user/add")
    @ResponseBody
    public Rest<?> add(@RequestBody UserInfo userInfo) {
        userInfoService.save(userInfo);
        return RestBody.ok("操作成功");
    }

    /**
     * Edit string.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     */
    @GetMapping("/system/user/edit/{userId}")
    public String edit(Model model, @PathVariable String userId) {
        UserInfo userInfo = userInfoService.findById(userId);
        model.addAttribute("userInfo", userInfo);
        return "system/user/edit";
    }

    /**
     * Edit rest.
     *
     * @param userInfo the user info
     * @return the rest
     */
    @PostMapping("/system/user/edit")
    @ResponseBody
    public Rest<?> edit(@RequestBody UserInfo userInfo) {
        // 帐号不可修改
        userInfo.setUsername(null);
        userInfoService.update(userInfo);
        return RestBody.ok("操作成功");
    }

    /**
     * Edit rest.
     *
     * @param userId the user id
     * @return the rest
     */
    @PostMapping("/system/user/enable/{userId}")
    @ResponseBody
    public Rest<?> edit(@PathVariable String userId) {
        userInfoService.enable(userId);
        return RestBody.ok("操作成功");
    }

    /**
     * Change password string.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     */
    @GetMapping("/system/user/password/{userId}")
    public String changePassword(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        return "system/user/password";
    }

    /**
     * Change password rest.
     *
     * @param passwordDTO the password dto
     * @return the rest
     */
    @PostMapping("/system/user/password")
    @ResponseBody
    public Rest<?> changePassword(@RequestBody UserPasswordDTO passwordDTO) {
        userInfoService.changePassword(passwordDTO);
        return RestBody.ok("操作成功");
    }
}
