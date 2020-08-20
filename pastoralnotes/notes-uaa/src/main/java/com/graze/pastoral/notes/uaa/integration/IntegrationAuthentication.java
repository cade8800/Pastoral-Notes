package com.graze.pastoral.notes.uaa.integration;

import lombok.Data;

import java.util.Map;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/12 15:38
 */
@Data
public class IntegrationAuthentication {

    /**
     * 登录类型  密码登录/手机验证码登录/小程序登录
     */
    private String authType;
    private String grantType;

    /**
     * 用户名/手机号
     */
    private String username;

    /**
     * 密码/验证码/OpenId
     */
    private String password;
    private String sessionKey;

    private String wxAppId;
    private String unionId;

    private Map<String, String[]> authParameters;

    public String getAuthParameter(String parameter) {
        String[] values = this.authParameters.get(parameter);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }
}
