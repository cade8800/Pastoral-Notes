package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/18 18:28
 */
@Data
public class UpdateUserPasswordInput {
    @NotBlank
    @Length(max = 32, min = 6)
    private String oldPassword;

    @NotBlank
    @Length(max = 32, min = 6)
    private String newPassword;

    @NotBlank
    @Length(max = 32, min = 6)
    private String confirmPassword;
}
