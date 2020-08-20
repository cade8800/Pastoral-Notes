package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-08-13 22:34
 */
@Data
public class GetSmsCaptchaInput {
    @NotBlank
    @Length(max = 20, message = "手机号码长度必须在{max}个字符内")
    private String mobile;
}
