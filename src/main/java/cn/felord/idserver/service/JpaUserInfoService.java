package cn.felord.idserver.service;

import cn.felord.idserver.entity.Role;
import cn.felord.idserver.entity.UserInfo;
import cn.felord.idserver.entity.dto.UserInfoDTO;
import cn.felord.idserver.entity.dto.UserPasswordDTO;
import cn.felord.idserver.entity.dto.UserRoleDTO;
import cn.felord.idserver.enumate.Enabled;
import cn.felord.idserver.enumate.Gender;
import cn.felord.idserver.enumate.RootUserConstants;
import cn.felord.idserver.exception.BindingException;
import cn.felord.idserver.exception.NotFoundException;
import cn.felord.idserver.mapstruct.UserInfoMapper;
import cn.felord.idserver.repository.RoleRepository;
import cn.felord.idserver.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Set;

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
    private final RoleRepository roleRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    public Page<UserInfo> page(Integer page, Integer limit) {
        page = Math.max(page - 1, 0);
        return userInfoRepository.findAll(PageRequest.of(page, limit, Sort.sort(UserInfo.class)
                .by(UserInfo::getUserId).descending()));
    }

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户未找到"));
    }

    @Override
    public UserInfo save(UserInfoDTO userInfoDTO) {
        if (RootUserConstants.ROOT_USERNAME.val().equals(userInfoDTO.getUsername())) {
            throw new BindingException("root用户不允许被创建");
        }
        UserInfo userInfo = new UserInfo();
        userInfoMapper.toUserInfo(userInfoDTO, userInfo);
        userInfo.setPassword(delegatingPasswordEncoder.encode(userInfo.getPassword()));
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
        if (RootUserConstants.ROOT_USERNAME.val().equals(userInfo.getUsername())) {
            throw new BindingException("root用户不允许被创建");
        }
        UserInfo target = userInfoRepository.findById(userInfo.getUserId())
                .orElseThrow(NotFoundException::new);
        this.userInfoMapper.mergeIgnorePasswordAndAuthorities(userInfo, target);
        this.userInfoRepository.flush();
    }

    @Override
    public void deleteById(String userId) {
        this.userInfoRepository.deleteById(userId);
    }

    @Override
    public void enable(String userId) {
        UserInfo target = userInfoRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        Boolean enabled = !target.getEnabled();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setEnabled(enabled);
        this.userInfoMapper.mergeIgnorePasswordAndAuthorities(userInfo, target);
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
                .orElseThrow(NotFoundException::new);
        if (delegatingPasswordEncoder.matches(passwordDTO.getOldPassword(), target.getPassword())) {

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setPassword(delegatingPasswordEncoder.encode(newPassword));
            this.userInfoMapper.mergeIgnoreAuthorities(userInfo, target);
            this.userInfoRepository.flush();

        } else {
            throw new BindingException("旧密码不匹配");
        }
    }

    @Override
    public void bindRoles(UserRoleDTO userRoleDTO) {
        UserInfo userInfo = userInfoRepository.findById(userRoleDTO.getUserId())
                .orElseThrow(NotFoundException::new);
        Set<Role> roles = roleRepository.findAllByRoleIdIn(userRoleDTO.getRoleIds());
        userInfo.setAuthorities(roles);
        this.userInfoRepository.flush();
    }
}
