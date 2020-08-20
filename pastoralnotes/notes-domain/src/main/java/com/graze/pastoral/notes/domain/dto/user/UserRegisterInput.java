package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/17 15:46
 */
@Data
public class UserRegisterInput extends UserMobilePhoneCaptchaLoginInput {
    @NotBlank
    @Length(max = 32)
    @Email
    private String mail;

    @NotBlank
    @Length(max = 32, min = 6)
    private String password;

    @NotBlank
    @Length(max = 32, min = 6)
    private String confirm;

    @NotBlank
    @Length(max = 6, min = 6, message = "验证码长度必须为{max}个字符")
    private String mailCaptcha;
}
