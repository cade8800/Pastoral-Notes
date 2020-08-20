package com.graze.pastoral.notes.domain.dto.user;

import lombok.Data;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 10:25
 */

@Data
public class MiniProgramLoginMsg {
    private String errcode;
    private String errmsg;
    private String session_key;
    private String openid;
    private String unionid;
}
