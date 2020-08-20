package com.graze.pastoral.notes.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pn_note")
public class NoteEntity extends BaseEntity {

    @Column(length = 128)
    private String title;

    @Column(length = 128)
    private String summary;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    public NoteEntity(String title, String summary, String coverUrl, String content, UUID createUserId) {
        this.title = title;
        this.summary = summary;
        this.coverUrl = coverUrl;
        this.content = content;
        this.setCreateUserId(createUserId);
    }
}
