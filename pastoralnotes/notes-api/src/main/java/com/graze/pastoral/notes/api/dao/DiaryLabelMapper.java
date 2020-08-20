package com.graze.pastoral.notes.api.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/5 17:41
 */
@Repository
public interface DiaryLabelMapper {
    void deleteDiaryLabelsByDiaryId(String diaryId, String currentUserId);
}
