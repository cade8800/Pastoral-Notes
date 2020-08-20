package com.graze.pastoral.notes.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.*;
import com.graze.pastoral.notes.api.repository.*;
import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryDetailOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryOutput;
import com.graze.pastoral.notes.domain.dto.diary.GetDiaryInput;
import com.graze.pastoral.notes.domain.dto.diary.UpdateDiaryDetailInput;
import com.graze.pastoral.notes.domain.dto.diary.GetDiaryInput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryDetailOutput;
import com.graze.pastoral.notes.domain.dto.diary.UpdateDiaryDetailInput;
import com.graze.pastoral.notes.domain.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import org.springframework.data.domain.Page;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:56
 */
@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private DiaryLabelRepository diaryLabelRepository;

    @Autowired
    private DiaryLabelMapper diaryLabelMapper;

    public BasePagingOutput<DiaryOutput> getDiary(GetDiaryInput input) {

        Date startDate = null;
        Date endDate = null;

        if (input.getStartDate() != null && input.getEndDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(input.getStartDate());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            startDate = calendar.getTime();

            calendar.setTime(input.getEndDate());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            endDate = calendar.getTime();
        }

        Page<DiaryOutput> diaryPage = PageHelper.startPage(input.getPageIndex(), input.getPageSize(), "create_time desc,update_time desc");
        diaryMapper.getDiaries(
                CurrentUserUtil.getCurrentLoginUser().getUserId().toString(),
                StringUtils.isBlank(input.getKeyword()) ? null : input.getKeyword().trim(),
                input.getLabelId() == null ? "" : input.getLabelId().toString(),
                startDate,
                endDate,
                input.getHasNoLabels()
        );

        BasePagingOutput<DiaryOutput> output = new BasePagingOutput<>();
        output.setTotalElements(diaryPage.getTotal());
        output.setTotalPages(diaryPage.getPages());
        output.setNumberOfElements(diaryPage.getPageSize());
        output.setPageNumber(diaryPage.getPageNum());

        List<DiaryOutput> outputList = diaryPage.getResult();
        outputList.forEach(t -> {
            if (t.getContent().length() > 150) {
                t.setContent(t.getContent().substring(0, 150));
            }
        });

        output.setContent(outputList);

        return output;
    }

    public DiaryDetailOutput getDiaryDetail(UUID diaryId) {
        DiaryDetailOutput output = diaryMapper.getDiaryDetail(
                diaryId.toString(),
                CurrentUserUtil.getCurrentLoginUser().getUserId().toString()
        );

        output.setLabels(
                labelMapper.getDiaryLabels(diaryId.toString())
        );

        return output;
    }

    @Transactional
    public void updateOrInsertDiary(UpdateDiaryDetailInput input) {
        UUID currentUserId = CurrentUserUtil.getCurrentLoginUser().getUserId();

        if (input.getId() == null) {
            DiaryEntity diary = new DiaryEntity(
                    Timestamp.from(input.getDate().toInstant()),
                    input.getLocation(),
                    input.getWeather(),
                    input.getSummary(),
                    input.getCoverUrl(),
                    input.getContent(),
                    currentUserId
            );
            diaryRepository.save(diary);

            input.getLabels().forEach(l -> {
                LabelEntity label = new LabelEntity();
                label.setId(l);
                DiaryLabelEntity dl = new DiaryLabelEntity(diary, label, currentUserId);
                diaryLabelRepository.save(dl);
            });

        } else {

            diaryMapper.updateDiary(
                    input.getId().toString(),
                    Timestamp.from(input.getDate().toInstant()),
                    input.getLocation(),
                    input.getWeather(),
                    input.getSummary(),
                    input.getCoverUrl(),
                    input.getContent(),
                    currentUserId.toString(),
                    Timestamp.from(ZonedDateTime.now().toInstant())
            );

            diaryLabelMapper.deleteDiaryLabelsByDiaryId(input.getId().toString(), currentUserId.toString());
            for (UUID labelId : input.getLabels()) {
                DiaryEntity n = new DiaryEntity();
                n.setId(input.getId());
                LabelEntity l = new LabelEntity();
                l.setId(labelId);
                DiaryLabelEntity nl = new DiaryLabelEntity();
                nl.setLabel(l);
                nl.setDiary(n);
                nl.setCreateUserId(currentUserId);
                diaryLabelRepository.save(nl);
            }
        }
    }

    public void deleteDiaryById(UUID diaryId) {
        diaryMapper.deleteDiaryById(diaryId.toString(), CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), Timestamp.from(ZonedDateTime.now().toInstant()));
    }
}
