package com.graze.pastoral.notes.domain.entity;


import com.graze.pastoral.notes.domain.type.UserGender;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-19 0:13
 */
@Entity
@Data
@Table(name = "pn_user")
public class UserEntity extends BaseEntity implements UserDetails, Serializable {


    private static final long serialVersionUID = -2776991936474479556L;
    @Column(name = "mobile_phone", length = 16)
    private String mobilePhone;

    /**
     * 昵称
     */
    @Column(name = "nickname", length = 32)
    private String nickname;

    /**
     * 头像Url
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 32)
    private UserGender gender = UserGender.UNKNOWN;

    @Column(name = "address", length = 150)
    private String address;

    /**
     * 城市
     */
    @Column(name = "city", length = 100)
    private String city;

    /**
     * 省份
     */
    @Column(name = "province", length = 100)
    private String province;

    /**
     * 国家
     */
    @Column(name = "country", length = 100)
    private String country;

    /**
     * 语言简写
     */
    @Column(name = "language", length = 10)
    private String language;

    /**
     * UNIONID
     */
    @Column(name = "union_id", length = 50)
    private String unionid;

    /**
     * OPENID
     */
    @Column(name = "open_id", length = 50)
    private String openid;

    /**
     * SESSION_KEY
     */
    @Column(name = "session_key", length = 50)
    private String sessionKey;

//    @Column(name = "expires_ing", length = 50)
//    private String expiresIng;

    @Column(name = "wx_app_id", length = 50)
    private String wxAppId;

    /**
     * 用户名称
     */
    @Column(name = "username", length = 128, unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

//    /**
//     * 用户类型
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(name = "user_type", length = 32, nullable = false)
//    private UserType userType = UserType.WECHAT_USER;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time", nullable = true)
    private Timestamp lastLoginTime = Timestamp.from(ZonedDateTime.now().toInstant());


    @Column(name = "is_account_non_expired", nullable = false)
    private Boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked", nullable = false)
    private Boolean isAccountNonLocked = true;

    @Column(name = "is_credentials_non_expired", nullable = false)
    private Boolean isCredentialsNonExpired = true;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;


    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "pn_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
