package com.graze.pastoral.notes.api.client;

import com.graze.pastoral.notes.domain.dto.auth.JWTDto;
import org.springframework.stereotype.Component;

@Component
public class UaaServiceHystrix implements UaaServiceClient {
    @Override
    public JWTDto getToken(String authorization,
                           String grantType,
                           String username,
                           String password,
                           String authType) {
        return null;
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
        return null;
    }
}
