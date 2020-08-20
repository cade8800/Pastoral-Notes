package com.graze.pastoral.notes.api.config;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserInfo {
    private UUID userId;
    private String username;
    private String nickname;
    private String avatarUrl;
}
