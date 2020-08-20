package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-08-19 1:44
 */
@Data
public class GetEmailCaptchaInput {
    @Email
    @NotBlank
    private String mail;
}
