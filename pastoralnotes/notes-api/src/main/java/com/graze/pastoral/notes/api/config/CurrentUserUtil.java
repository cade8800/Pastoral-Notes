package com.graze.pastoral.notes.api.config;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.UUID;

public class CurrentUserUtil {
    /**
     * 获取当前登录人的信息
     *
     * @return
     * @author FYK
     */
    public static LoginUserInfo getCurrentLoginUser() {

        LoginUserInfo loginUserInfo = new LoginUserInfo();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        @SuppressWarnings("unchecked")
        Map<String, ?> map = (Map<String, ?>) authentication.getPrincipal();

//        BeanMap beanMap = BeanMap.create(loginUserInfo);
//        beanMap.putAll(map);

        map.forEach((k, v) -> {
            switch (k) {
                case "userId":
                    UUID userId = v != null ? UUID.fromString(v.toString()) : null;
                    loginUserInfo.setUserId(userId);
                    break;
                case "nickname":
                    String nickname = v != null ? v.toString() : "";
                    loginUserInfo.setNickname(nickname);
                    break;
                case "username":
                    String username = v != null ? v.toString() : "";
                    loginUserInfo.setUsername(username);
                    break;
                case "avatarUrl":
                    String avatarUrl = v != null ? v.toString() : "";
                    loginUserInfo.setAvatarUrl(avatarUrl);
                    break;
            }
        });

        if (loginUserInfo.getUserId() == null)
            throw new RuntimeException("INVALID USER");

        return loginUserInfo;
    }
}
