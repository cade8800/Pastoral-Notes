package com.graze.pastoral.notes.api.dao;

import com.graze.pastoral.notes.domain.dto.user.UserInfoDto;
import com.graze.pastoral.notes.domain.type.UserGender;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-23 21:46
 */
@Repository
public interface UserMapper {
    UserInfoDto getUserInfo(String userId);

    void updateUserInfo(String userId,
                        String nickname,
                        UserGender gender,
                        String country,
                        String province,
                        String city,
                        String address,
                        String avatarUrl
    );

    String getUserPassword(String userId);

    void updateUserPassword(String userId, String newPassword);
}
