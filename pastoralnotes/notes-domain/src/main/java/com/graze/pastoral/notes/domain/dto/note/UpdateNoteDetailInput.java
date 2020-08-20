package com.graze.pastoral.notes.domain.dto.note;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/3 10:05
 */
@Data
public class UpdateNoteDetailInput extends NoteOutput {
    private List<UUID> labels;
    private List<UUID> categories;
}
