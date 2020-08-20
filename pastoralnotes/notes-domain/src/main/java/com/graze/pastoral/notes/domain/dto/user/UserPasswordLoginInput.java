package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 10:23
 */
@Data
public class UserPasswordLoginInput {
    @NotBlank(message = "用户名不可为空")
    @Length(max = 128)
    @Email
    private String username;

    @NotBlank(message = "密码不可为空")
    @Length(max = 255)
    private String password;
}
