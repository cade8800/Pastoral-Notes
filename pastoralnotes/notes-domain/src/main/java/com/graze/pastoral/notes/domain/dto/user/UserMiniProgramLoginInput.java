package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/14 16:27
 */
@Data
public class UserMiniProgramLoginInput {
    @NotBlank
    private String code;
    @NotBlank
    private String encryptedData;
    @NotBlank
    private String iv;
}
