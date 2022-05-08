package cn.felord.idserver.service;

import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.entity.dto.UserPasswordDTO;
import cn.felord.idserver.enumate.Enabled;
import cn.felord.idserver.enumate.Gender;
import cn.felord.idserver.exception.BindingException;
import cn.felord.idserver.mapstruct.UserInfoMapper;
import cn.felord.idserver.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class JpaUserInfoService implements UserInfoService {
    private static final PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

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
        if (userInfo.getGender() == null) {
            userInfo.setGender(Integer.valueOf(Gender.UNKNOWN.val()));
        }
        if (userInfo.getEnabled() == null) {
            userInfo.setEnabled(Boolean.valueOf(Enabled.ENABLE.val()));
        }
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void update(final UserInfo userInfo) {
        UserInfo target = userInfoRepository.findById(userInfo.getUserId())
                .orElseThrow(RuntimeException::new);
        this.userInfoMapper.merge(userInfo, target);
        this.userInfoRepository.flush();
    }

    @Override
    public void enable(String userId) {
        UserInfo target = userInfoRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        Boolean enabled = !target.getEnabled();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setEnabled(enabled);
        this.userInfoMapper.merge(userInfo, target);
        this.userInfoRepository.flush();
    }

    @Override
    public UserInfo findById(String userId) {
        return userInfoRepository.findById(userId).orElse(null);
    }

    @Override
    public void changePassword(UserPasswordDTO passwordDTO) {

        String newPassword = passwordDTO.getNewPassword();
        if (ObjectUtils.isEmpty(newPassword) || !newPassword.equals(passwordDTO.getConfirmPassword())) {
            throw new BindingException("两次密码不一致");
        }

        String userId = passwordDTO.getUserId();
        UserInfo target = userInfoRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
        if (delegatingPasswordEncoder.matches(passwordDTO.getOldPassword(), target.getSecret())) {

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setSecret(delegatingPasswordEncoder.encode(newPassword));
            this.userInfoMapper.merge(userInfo, target);
            this.userInfoRepository.flush();

        } else {
            throw new BindingException("旧密码不匹配");
        }
    }
}
