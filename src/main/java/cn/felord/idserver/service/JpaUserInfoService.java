package cn.felord.idserver.service;

import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.enumate.Enabled;
import cn.felord.idserver.enumate.Gender;
import cn.felord.idserver.repository.UserInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
public class JpaUserInfoService implements UserInfoService {
    private final PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserInfoRepository userInfoRepository;

    public JpaUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Page<UserInfo> page(Integer page, Integer limit) {
        page = Math.max(page - 1, 0);
        return userInfoRepository.findAll(PageRequest.of(page, limit, Sort.sort(UserInfo.class)
                .by(UserInfo::getUserId).descending()));
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        userInfo.setUserId(null);
        //todo 303校验
        userInfo.setSecret(delegatingPasswordEncoder.encode(userInfo.getSecret()));
        if (userInfo.getGender()==null) {
            userInfo.setGender(Integer.valueOf(Gender.UNKNOWN.val()));
        }
        if (userInfo.getEnabled()==null) {
            userInfo.setEnabled(Boolean.valueOf(Enabled.ENABLE.val()));
        }
        return userInfoRepository.save(userInfo);
    }
}
