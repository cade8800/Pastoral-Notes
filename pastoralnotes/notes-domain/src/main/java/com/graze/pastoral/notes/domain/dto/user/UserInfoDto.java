package com.graze.pastoral.notes.domain.dto.user;

import com.graze.pastoral.notes.domain.type.UserGender;
import lombok.Data;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/18 15:13
 */
@Data
public class UserInfoDto {
    private String mobilePhone;
    private String username;

    private String nickname;
    private String avatarUrl;
    private UserGender gender;
    private String city;
    private String province;
    private String country;
    private String address;
}
