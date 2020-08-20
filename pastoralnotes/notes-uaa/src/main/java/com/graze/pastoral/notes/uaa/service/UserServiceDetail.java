package com.graze.pastoral.notes.uaa.service;

import com.graze.pastoral.notes.domain.dto.auth.AuthType;
import com.graze.pastoral.notes.domain.entity.UserEntity;
import com.graze.pastoral.notes.uaa.dao.UserRepository;
import com.graze.pastoral.notes.uaa.integration.IntegrationAuthentication;
import com.graze.pastoral.notes.uaa.integration.IntegrationAuthenticationContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-19 0:54
 */

@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        if (integrationAuthentication.getAuthType().equals(AuthType.password)) {
            return userPasswordLogin(integrationAuthentication);
        } else if (integrationAuthentication.getAuthType().equals(AuthType.mobileCaptcha)) {
            return userMobilePhoneCaptchaLogin(integrationAuthentication);
        } else if (integrationAuthentication.getAuthType().equals(AuthType.miniProgram)) {
            return userMiniProgramLogin(integrationAuthentication);
        }
        throw new UsernameNotFoundException("INVALID REQUEST");
    }

    /**
     * 用户账号密码登录
     *
     * @param integrationAuthentication
     * @return
     */
    private UserEntity userPasswordLogin(IntegrationAuthentication integrationAuthentication) {
        UserEntity userEntity = userRepository.findByUsernameAndIsDeletedFalse(integrationAuthentication.getUsername());
        if (userEntity.getIsDeleted() == false &&
                userEntity.getIsAccountNonExpired() == true &&
                userEntity.getIsAccountNonLocked() == true &&
                userEntity.getIsCredentialsNonExpired() == true &&
                userEntity.getIsEnabled() == true) {
            userEntity.setLastLoginTime(Timestamp.from(ZonedDateTime.now().toInstant()));
            userRepository.save(userEntity);
        }
        return userEntity;
    }

    /**
     * 用户小程序登录
     *
     * @param integrationAuthentication
     * @return
     */
    private UserEntity userMiniProgramLogin(IntegrationAuthentication integrationAuthentication) {
        UserEntity userEntity = userRepository.findByMobilePhoneAndIsDeletedFalse(integrationAuthentication.getUsername());
        if (userEntity == null) {
            userEntity = addNewUserByMiniProgram(integrationAuthentication);
        } else {
            this.throwInvalidUser(userEntity);

            if (StringUtils.isBlank(userEntity.getOpenid()))
                userEntity.setOpenid(integrationAuthentication.getPassword());

            if (StringUtils.isBlank((userEntity.getWxAppId())))
                userEntity.setWxAppId(integrationAuthentication.getWxAppId());

            if (StringUtils.isBlank((userEntity.getUnionid())))
                userEntity.setUnionid(integrationAuthentication.getUnionId());

            userEntity.setLastLoginTime(Timestamp.from(ZonedDateTime.now().toInstant()));
            userEntity.setSessionKey(integrationAuthentication.getSessionKey());
            userRepository.save(userEntity);
        }

        userEntity.setUsername(integrationAuthentication.getUsername());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(integrationAuthentication.getPassword()));
        return userEntity;
    }

    /**
     * 检查是否无效用户
     *
     * @param userEntity
     */
    private void throwInvalidUser(UserEntity userEntity) {
        if (userEntity.getIsDeleted() == true ||
                userEntity.getIsAccountNonExpired() == false ||
                userEntity.getIsAccountNonLocked() == false ||
                userEntity.getIsCredentialsNonExpired() == false ||
                userEntity.getIsEnabled() == false) {
            throw new UsernameNotFoundException("无效用户");
        }
    }

    /**
     * 新增由小程序登录的用户
     *
     * @param integrationAuthentication
     * @return
     */
    private UserEntity addNewUserByMiniProgram(IntegrationAuthentication integrationAuthentication) {
        UserEntity userEntity = new UserEntity();

        userEntity.setMobilePhone(integrationAuthentication.getUsername());
        userEntity.setOpenid(integrationAuthentication.getPassword());
        userEntity.setWxAppId(integrationAuthentication.getWxAppId());
        userEntity.setUnionid(integrationAuthentication.getUnionId());

        userEntity.setSessionKey(integrationAuthentication.getSessionKey());

        userRepository.save(userEntity);
        return userEntity;
    }

    /**
     * 用户手机验证码登录
     *
     * @param integrationAuthentication
     * @return
     */
    private UserEntity userMobilePhoneCaptchaLogin(IntegrationAuthentication integrationAuthentication) {
        UserEntity userEntity = userRepository.findByMobilePhoneAndIsDeletedFalse(integrationAuthentication.getUsername());
        if (userEntity == null) {
//            userEntity = this.addNewUserByMobilePhone(integrationAuthentication);
            return null;//账户不存在，请走注册流程
        } else {
            this.throwInvalidUser(userEntity);
            userEntity.setLastLoginTime(Timestamp.from(ZonedDateTime.now().toInstant()));
            userRepository.save(userEntity);
        }

        userEntity.setUsername(integrationAuthentication.getUsername());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(integrationAuthentication.getPassword()));
        return userEntity;
    }

    /**
     * [已弃用，新用户仅能通过注册]新增通过手机登录的用户
     *
     * @param integrationAuthentication
     * @return
     */
    @Deprecated
    private UserEntity addNewUserByMobilePhone(IntegrationAuthentication integrationAuthentication) {
        UserEntity userEntity = new UserEntity();

//        userEntity.setUserType(UserType.SYSTEM_USER);
        userEntity.setMobilePhone(integrationAuthentication.getUsername());

        userRepository.save(userEntity);
        return userEntity;
    }
}
