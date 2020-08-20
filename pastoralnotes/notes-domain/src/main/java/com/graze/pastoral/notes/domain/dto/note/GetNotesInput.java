package com.graze.pastoral.notes.domain.dto.note;

import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingInput;
import lombok.Data;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:41
 */
@Data
public class GetNotesInput extends BasePagingInput {
    private String keyword;
    private UUID labelId;
    private UUID categoryId;

    private Boolean hasNoLabels;
    private Boolean hasNoCategories;
}
