package com.graze.pastoral.notes.domain.dto.diary;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/3 10:05
 */
@Data
public class UpdateDiaryDetailInput extends DiaryOutput {
    private List<UUID> labels;
}
