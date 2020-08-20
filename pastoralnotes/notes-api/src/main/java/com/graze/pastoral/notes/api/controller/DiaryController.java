package com.graze.pastoral.notes.api.controller;

import com.graze.pastoral.notes.api.service.DiaryService;
import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryDetailOutput;
import com.graze.pastoral.notes.domain.dto.diary.DiaryOutput;
import com.graze.pastoral.notes.domain.dto.diary.GetDiaryInput;
import com.graze.pastoral.notes.domain.dto.diary.UpdateDiaryDetailInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:58
 */
@RestController
@RequestMapping("diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("/get")
    public BasePagingOutput<DiaryOutput> getDiary(@Valid @RequestBody GetDiaryInput input) {
        return diaryService.getDiary(input);
    }

    @GetMapping("/get/{id}")
    public DiaryDetailOutput getDiaryDetailById(@PathVariable UUID id) {
        return diaryService.getDiaryDetail(id);
    }

    @PutMapping("get")
    public void updateOrInsertDiary(@Valid @RequestBody UpdateDiaryDetailInput input) {
        diaryService.updateOrInsertDiary(input);
    }

    @DeleteMapping("get/{id}")
    public void deleteDiaryById(@PathVariable UUID id) {
        diaryService.deleteDiaryById(id);
    }
}
