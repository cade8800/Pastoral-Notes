package com.graze.pastoral.notes.domain.dto.note;

import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import com.graze.pastoral.notes.domain.dto.label.LabelOutput;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/28 16:27
 */
@Data
public class NotesDetailOutput extends NoteOutput {
    private List<LabelOutput> labels;
    private List<CategoryOutput> categories;
}
