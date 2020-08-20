package com.graze.pastoral.notes.api.service;


import com.graze.pastoral.notes.api.client.UaaServiceClient;
import com.graze.pastoral.notes.api.client.WxServiceClient;
import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.UserMapper;
import com.graze.pastoral.notes.api.repository.UserRepository;
import com.graze.pastoral.notes.api.utils.wxHelper;
import com.graze.pastoral.notes.domain.dto.auth.AuthType;
import com.graze.pastoral.notes.domain.dto.auth.JWTDto;
import com.graze.pastoral.notes.domain.dto.user.*;
import com.graze.pastoral.notes.domain.entity.UserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UaaServiceClient uaaServiceClient;

    @Value("${loginClient.clientId}")
    private String loginClientId;

    @Value("${loginClient.secret}")
    private String loginClientSecret;

    @Autowired
    private WxServiceClient wxServiceClient;

    @Value("${weChatMiniProgram.appId}")
    private String appId;
    @Value("${weChatMiniProgram.appSercret}")
    private String appSercret;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    private JWTDto getTokenByPassword(String username, String password, String authType) {
        String authorization = "Basic " + Base64.getEncoder().encodeToString((loginClientId + ":" + loginClientSecret).getBytes());
        JWTDto jwtDto = uaaServiceClient.getToken(authorization, "password", username, password, authType);
        if (jwtDto == null) {
            throw new RuntimeException("网络错误，请稍后再试");
        }
        return jwtDto;
    }

    private JWTDto getTokenByPassword(String username, String password, String authType, String sessionKey, String wxAppId, String unionId) {
        String authorization = "Basic " + Base64.getEncoder().encodeToString((loginClientId + ":" + loginClientSecret).getBytes());
        JWTDto jwtDto = uaaServiceClient.getToken(
                authorization,
                "password",
                username,
                password,
                authType,
                sessionKey,
                wxAppId,
                unionId);
        if (jwtDto == null) {
            throw new RuntimeException("网络错误，请稍后再试");
        }
        return jwtDto;
    }

    public JWTDto userPasswordLogin(UserPasswordLoginInput input) {
        JWTDto jwtDto = this.getTokenByPassword(input.getUsername(), input.getPassword(), AuthType.password);
        if (!StringUtils.isBlank(jwtDto.getError())) {
            if (jwtDto.getError().equals("unauthorized")) {
                throw new RuntimeException("用户不存在，请先注册");
            }
            if (jwtDto.getError().equals("invalid_grant")) {
                throw new RuntimeException("用户名或密码错误");
            }
        }
        return jwtDto;
    }

    public JWTDto userMiniProgramLogin(UserMiniProgramLoginInput input) {

        MiniProgramLoginMsg miniProgramLoginMsg = this.miniProgramLogin(input.getCode());
        MiniProgramPhoneOutput miniProgramPhoneOutput = wxHelper.getMiniProgramMobilePhone(miniProgramLoginMsg.getSession_key(), input.getEncryptedData(), input.getIv());

        JWTDto jwtDto = getTokenByPassword(
                miniProgramPhoneOutput.getPurePhoneNumber(),
                miniProgramLoginMsg.getOpenid(),
                AuthType.miniProgram,
                miniProgramLoginMsg.getSession_key(),
                appId,
                miniProgramLoginMsg.getUnionid()
        );

        return jwtDto;
    }

    /**
     * 微信小程序登录 用code换取openId、session_key
     *
     * @param code
     * @return
     */
    private MiniProgramLoginMsg miniProgramLogin(String code) {
        MiniProgramLoginMsg msg = wxServiceClient.authorizationCode(appId, appSercret, code);
        if (!StringUtils.isBlank(msg.getErrcode())) {
            throw new RuntimeException(msg.getErrmsg());
        }
        return msg;
    }

    public JWTDto userMobilePhoneCaptchaLogin(UserMobilePhoneCaptchaLoginInput input) {
        redisService.checkSmsCaptcha(input.getMobile(), input.getCaptcha());
        JWTDto jwtDto = getTokenByPassword(input.getMobile(), input.getCaptcha(), AuthType.mobileCaptcha);
        if (!StringUtils.isBlank(jwtDto.getError())) {
            throw new RuntimeException("用户不存在，请先注册");
        }
        return jwtDto;
    }

    public void getSmsCaptcha(GetSmsCaptchaInput input) {
        String captcha = redisService.setSmsCaptcha(input.getMobile());
        smsService.sendSms(input.getMobile(), captcha);
    }

    public void userRegister(UserRegisterInput input) {
        if (!input.getPassword().equals(input.getConfirm())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        redisService.checkEmailCaptcha(input.getMail(), input.getMailCaptcha());
        redisService.checkSmsCaptcha(input.getMobile(), input.getCaptcha());

        if (userRepository.countByUsernameOrMobilePhone(input.getMail(), input.getMobile()) > 0) {
            throw new RuntimeException("用户已存在,请直接登录");
        }

        UserEntity user = new UserEntity();
        user.setUsername(input.getMail());
        user.setMobilePhone(input.getMobile());
        user.setPassword(new BCryptPasswordEncoder().encode(input.getPassword()));
        userRepository.save(user);
    }

    public UserInfoDto getUserInfo() {
        return userMapper.getUserInfo(CurrentUserUtil.getCurrentLoginUser().getUserId().toString());
    }

    public void updateUserInfo(UserInfoDto input) {
        userMapper.updateUserInfo(
                CurrentUserUtil.getCurrentLoginUser().getUserId().toString(),
                input.getNickname(),
                input.getGender(),
                input.getCountry(),
                input.getProvince(),
                input.getCity(),
                input.getAddress(),
                input.getAvatarUrl()
        );
    }

    public void updateUserPassword(UpdateUserPasswordInput input) {
        if (!input.getConfirmPassword().equals(input.getNewPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        String userPassword = userMapper.getUserPassword(CurrentUserUtil.getCurrentLoginUser().getUserId().toString());
        if (!new BCryptPasswordEncoder().matches(input.getOldPassword(), userPassword)) {
            throw new RuntimeException("原始密码错误");
        }
        userMapper.updateUserPassword(CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), new BCryptPasswordEncoder().encode(input.getNewPassword()));
    }

    public void getEmailCaptcha(GetEmailCaptchaInput input) throws MessagingException {
        String captcha = redisService.setEmailCaptcha(input.getMail());
        mailService.sendMailCaptcha(input.getMail(), "【田园笔记 - PastoralNotes】验证码", captcha);
    }
}
