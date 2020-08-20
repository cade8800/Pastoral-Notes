package com.graze.pastoral.notes.api.dao;

import com.graze.pastoral.notes.domain.dto.label.LabelOutput;
import com.graze.pastoral.notes.domain.type.LabelType;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-23 21:46
 */
@Repository
public interface LabelMapper {
    List<LabelOutput> getLabels(String createUserId, String labelType);

    List<String> getNotesLabelIds(String noteId);

    List<LabelOutput> getNoteLabels(String noteId);

    void updateLabel(String labelId, String name, String updateUserId, Timestamp updateTime);

    Integer countLabelByName(String name, String type, String createUserId);

    String getLabelNameById(String labelId);

    void deleteLabelById(String labelId, String createUserId, Timestamp updateTime);

    List<LabelOutput> getDiaryLabels(String diaryId);
}
