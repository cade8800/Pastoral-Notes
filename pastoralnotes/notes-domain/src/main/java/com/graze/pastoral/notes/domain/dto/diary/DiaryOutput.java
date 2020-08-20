package com.graze.pastoral.notes.domain.dto.diary;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:41
 */
@Data
public class DiaryOutput {
    private UUID id;

    private Date date;
    private String location;
    private String weather;
    private String summary;

    private String coverUrl;
    private String content;
    private Date createTime;
    private Date updateTime;
}
