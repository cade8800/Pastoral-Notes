package com.graze.pastoral.notes.uaa.dao;

import com.graze.pastoral.notes.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsernameAndIsDeletedFalse(String username);

    UserEntity findByMobilePhoneAndIsDeletedFalse(String mobilePhone);

//    @Query(value = "select * from pn_user " +
//            "where wx_app_id=?1 " +
//            "and open_id=?2 " +
//            "and user_type='WECHAT_USER' " +
//            "limit 1", nativeQuery = true)
//    UserEntity getWxUser(String wxAppId, String openId);

    UserEntity findByWxAppIdAndOpenidAndIsDeletedFalse(String wxAppId, String openid);
}
