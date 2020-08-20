package com.graze.pastoral.notes.api.dao;

import com.graze.pastoral.notes.domain.dto.diary.DiaryDetailOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryOutput;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/5 17:41
 */
@Repository
public interface DiaryMapper {
    List<DiaryOutput> getDiaries(String currentUserId, String keyword, String labelId, Date startDate, Date endDate, Boolean hasNoLabels);

    DiaryDetailOutput getDiaryDetail(String diaryId, String currentUserId);

    void deleteDiaryById(String diaryId, String currentUserId, Timestamp updateTime);

    void updateDiary(String diaryId, Timestamp date, String location, String weather, String summary, String coverUrl, String content, String currentUserId, Timestamp updateTime);
}
