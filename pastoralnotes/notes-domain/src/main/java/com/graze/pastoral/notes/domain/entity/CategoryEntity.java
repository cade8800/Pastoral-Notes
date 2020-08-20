package com.graze.pastoral.notes.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 10:48
 */
@Getter
@Setter
@Entity
@Table(name = "pn_category")
public class CategoryEntity extends BaseEntity {
    @Column(length = 64)
    private String name;
}
