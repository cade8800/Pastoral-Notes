package com.graze.pastoral.notes.domain.dto.diary;

import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingInput;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:41
 */
@Data
public class GetDiaryInput extends BasePagingInput {
    private String keyword;
    private UUID labelId;

    private Date startDate;
    private Date endDate;

    private Boolean hasNoLabels;
}
