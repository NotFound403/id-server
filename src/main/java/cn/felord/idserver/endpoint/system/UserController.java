package cn.felord.idserver.endpoint.system;

import cn.felord.idserver.advice.BaseController;
import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
private UserInfoRepository userInfoRepository;

    /**
     * Main string.
     *
     * @return the string
     */
    @GetMapping("/system/user/mian")
    public String main() {
        return  "system/user/main";
    }


    @GetMapping("/system/user/data")
        @ResponseBody
        public Page<UserInfo> page(@RequestParam Integer page, @RequestParam Integer limit) {
                         page= Math.max(page - 1, 0);
            return userInfoRepository.findAll(PageRequest.of(page, limit, Sort.sort(UserInfo.class)
                    .by(UserInfo::getUserId).descending()));
        }

}
