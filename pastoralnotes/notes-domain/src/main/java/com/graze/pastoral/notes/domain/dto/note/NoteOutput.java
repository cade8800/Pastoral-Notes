package com.graze.pastoral.notes.domain.dto.note;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:41
 */
@Data
public class NoteOutput {
    private UUID id;
    private String title;
    private String summary;
    private String coverUrl;
    private String content;
    private Date createTime;
    private Date updateTime;
}
