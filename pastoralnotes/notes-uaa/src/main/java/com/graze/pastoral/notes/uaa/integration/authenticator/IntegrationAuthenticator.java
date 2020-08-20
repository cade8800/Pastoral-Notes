package com.graze.pastoral.notes.uaa.integration.authenticator;

import com.graze.pastoral.notes.domain.dto.auth.SysUserAuthentication;
import com.graze.pastoral.notes.uaa.integration.IntegrationAuthentication;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/12 15:40
 */
public interface IntegrationAuthenticator {

    /**
     * 处理集成认证
     * @param integrationAuthentication
     * @return
     */
    SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication);


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /** 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);

}
