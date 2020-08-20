package com.graze.pastoral.notes.domain.entity;

import com.graze.pastoral.notes.domain.type.LabelType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 10:49
 */
@Getter
@Setter
@Entity
@Table(name = "pn_label")
public class LabelEntity extends BaseEntity {
    @Column(length = 64)
    private String name;

    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private LabelType type;
}
