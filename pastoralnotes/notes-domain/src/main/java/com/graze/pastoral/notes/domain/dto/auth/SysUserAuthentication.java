package com.graze.pastoral.notes.domain.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/12 15:47
 */
@Data
public class SysUserAuthentication implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String status;

    private String name;

    private String type;
}
