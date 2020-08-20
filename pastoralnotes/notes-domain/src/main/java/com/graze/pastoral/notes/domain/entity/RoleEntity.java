package com.graze.pastoral.notes.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-19 0:30
 */
@Entity
@Getter
@Setter
@Table(name = "pn_role")
public class RoleEntity extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 4154815818657002799L;
    @Column(nullable = false, length = 128)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
