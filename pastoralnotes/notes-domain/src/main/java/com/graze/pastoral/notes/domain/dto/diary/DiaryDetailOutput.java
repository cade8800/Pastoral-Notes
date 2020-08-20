package com.graze.pastoral.notes.domain.dto.diary;

import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import com.graze.pastoral.notes.domain.dto.label.LabelOutput;
import lombok.Data;

import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/28 16:27
 */
@Data
public class DiaryDetailOutput extends DiaryOutput {
    private List<LabelOutput> labels;
}
