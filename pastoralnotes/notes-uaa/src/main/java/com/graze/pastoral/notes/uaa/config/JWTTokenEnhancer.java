package com.graze.pastoral.notes.uaa.config;

import com.graze.pastoral.notes.domain.entity.UserEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther EEDC
 * @Date 2020-07-19 0:08
 */

public class JWTTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserEntity) {
            UserEntity user = (UserEntity) principal;
            Map<String, Object> info = new HashMap<>();
            info.put("userId", user.getId());
            info.put("nickname", user.getNickname());
            info.put("username", user.getUsername());
            info.put("avatarUrl", user.getAvatarUrl());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        }

        return accessToken;
    }
}
