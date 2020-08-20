package com.graze.pastoral.notes.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pn_diary_label")
public class DiaryLabelEntity extends BaseEntity {
    @JoinColumn(name = "diary_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DiaryEntity diary;

    @JoinColumn(name = "label_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LabelEntity label;

    public DiaryLabelEntity(DiaryEntity diary, LabelEntity label, UUID createUserId) {
        this.diary = diary;
        this.label = label;
        this.setCreateUserId(createUserId);
    }
}
