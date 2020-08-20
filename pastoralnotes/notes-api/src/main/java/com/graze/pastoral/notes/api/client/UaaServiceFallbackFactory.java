package com.graze.pastoral.notes.api.client;

import com.alibaba.fastjson.JSONObject;
import com.graze.pastoral.notes.domain.dto.auth.JWTDto;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/13 10:47
 */
@Component
public class UaaServiceFallbackFactory implements FallbackFactory<UaaServiceClient> {
    @Override
    public UaaServiceClient create(Throwable cause) {

        return new UaaServiceClient() {
            @Override
            public JWTDto getToken(String authorization,
                                   String grantType,
                                   String username,
                                   String password,
                                   String authType) {
                JWTDto jwtDto = null;
                if (cause != null && !StringUtils.isBlank(cause.getMessage())) {
                    jwtDto = JSONObject.parseObject(cause.getMessage(), JWTDto.class);
                }
                return jwtDto;
            }

            @Override
            public JWTDto getToken(String authorization,
                                   String grantType,
                                   String username,
                                   String password,
                                   String authType,
                                   String sessionKey,
                                   String wxAppId,
                                   String unionId) {
                JWTDto jwtDto = null;
                if (cause != null && !StringUtils.isBlank(cause.getMessage())) {
                    jwtDto = JSONObject.parseObject(cause.getMessage(), JWTDto.class);
                }
                return jwtDto;
            }
        };

    }
}
