package com.graze.pastoral.notes.domain.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 10:10
 */
@Data
public class JWTDto implements Serializable {
    private String access_token, token_type, refresh_token, scope, jti;
    private int expires_in;
    private String error, error_description;
}
