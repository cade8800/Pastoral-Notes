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
@Table(name = "pn_note_category")
public class NoteCategoryEntity extends BaseEntity {
    @JoinColumn(name = "note_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private NoteEntity note;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    public NoteCategoryEntity(NoteEntity note, CategoryEntity category, UUID createUserId) {
        this.note = note;
        this.category = category;
        this.setCreateUserId(createUserId);
    }
}
