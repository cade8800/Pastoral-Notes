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
@Table(name = "pn_note_label")
public class NoteLabelEntity extends BaseEntity {
    @JoinColumn(name = "note_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private NoteEntity note;

    @JoinColumn(name = "label_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LabelEntity label;

    public NoteLabelEntity(NoteEntity note, LabelEntity label, UUID createUserId) {
        this.note = note;
        this.label = label;
        this.setCreateUserId(createUserId);
    }
}
