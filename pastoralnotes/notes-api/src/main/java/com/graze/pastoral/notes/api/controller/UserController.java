package com.graze.pastoral.notes.api.controller;

import com.graze.pastoral.notes.api.client.WxServiceClient;
import com.graze.pastoral.notes.api.service.UserService;
import com.graze.pastoral.notes.domain.dto.auth.JWTDto;
import com.graze.pastoral.notes.domain.dto.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxServiceClient wxServiceClient;

    @PostMapping("login/m")
    public JWTDto userMiniProgramLogin(@Valid @RequestBody UserMiniProgramLoginInput input) {
        input.setCode(input.getCode().trim());
        input.setEncryptedData(input.getEncryptedData().trim());
        input.setIv(input.getIv().trim());
        return userService.userMiniProgramLogin(input);
    }

    @PostMapping("login")
    public JWTDto userPasswordLogin(@Valid @RequestBody UserPasswordLoginInput input) {
        input.setPassword(input.getPassword().trim());
        input.setUsername(input.getUsername().trim());
        return userService.userPasswordLogin(input);
    }

    @PostMapping("login/c")
    public JWTDto userMobilePhoneCaptchaLogin(@Valid @RequestBody UserMobilePhoneCaptchaLoginInput input) {
        input.setCaptcha(input.getCaptcha().trim());
        input.setMobile(input.getMobile().trim());
        return userService.userMobilePhoneCaptchaLogin(input);
    }

    @PostMapping("login/v/s")
    public void getSmsCaptcha(@Valid @RequestBody GetSmsCaptchaInput input) {
        input.setMobile(input.getMobile().trim());
        userService.getSmsCaptcha(input);
    }

    @PostMapping("login/v/e")
    public void getEmailCaptcha(@Valid @RequestBody GetEmailCaptchaInput input) throws MessagingException {
        input.setMail(input.getMail().trim());
        userService.getEmailCaptcha(input);
    }

    @PostMapping("register")
    public void userRegister(@Valid @RequestBody UserRegisterInput input) {
        input.setConfirm(input.getConfirm().trim());
        input.setMail(input.getMail().trim());
        input.setPassword(input.getPassword().trim());
        input.setCaptcha(input.getCaptcha().trim());
        input.setMobile(input.getMobile().trim());
        userService.userRegister(input);
    }

    @GetMapping("info/get")
    public UserInfoDto getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("info/get")
    public void updateUserInfo(@Valid @RequestBody UserInfoDto input) {
        userService.updateUserInfo(input);
    }

    @PutMapping("p")
    public void updateUserPassword(@Valid @RequestBody UpdateUserPasswordInput input) {
        input.setConfirmPassword(input.getConfirmPassword());
        input.setNewPassword(input.getNewPassword());
        input.setOldPassword(input.getOldPassword());
        userService.updateUserPassword(input);
    }
}
