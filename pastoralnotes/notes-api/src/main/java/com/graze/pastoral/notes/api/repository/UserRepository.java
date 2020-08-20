package com.graze.pastoral.notes.api.repository;

import com.graze.pastoral.notes.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = "select * from pn_user " +
            "where username=?1 " +
            "and is_deleted=0 " +
            "and is_account_non_expired=1 " +
            "and is_account_non_locked=1 " +
            "and is_credentials_non_expired=1 " +
            "and is_enabled=1 " +
            "limit 1", nativeQuery = true)
    UserEntity systemUserLogin(String username);

    @Query(value = "select * from pn_user " +
            "where username=?1 " +
            "and open_id=?1 " +
            "limit 1", nativeQuery = true)
    UserEntity wxUserLogin(String openId);

    Integer countByUsernameOrMobilePhone(String username, String mobilePhone);
}
