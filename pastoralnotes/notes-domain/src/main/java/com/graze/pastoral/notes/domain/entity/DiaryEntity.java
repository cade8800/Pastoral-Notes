package com.graze.pastoral.notes.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pn_diary")
public class DiaryEntity extends BaseEntity {

    @Column(nullable = false)
    private Timestamp date;

    @Column(length = 128)
    private String location;

    @Column(length = 128)
    private String weather;

    @Column(length = 128)
    private String summary;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    public DiaryEntity(Timestamp date, String location, String weather, String summary, String coverUrl, String content, UUID createUserId) {
        this.date = date;
        this.location = location;
        this.weather = weather;
        this.summary = summary;
        this.coverUrl = coverUrl;
        this.content = content;
        this.setCreateUserId(createUserId);
    }
}
