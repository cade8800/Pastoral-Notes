package com.graze.pastoral.notes.api.controller;

import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.LabelMapper;
import com.graze.pastoral.notes.api.service.LabelService;
import com.graze.pastoral.notes.domain.dto.label.LabelOutput;
import com.graze.pastoral.notes.domain.type.LabelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/24 10:07
 */
@RestController
@RequestMapping("label")
public class LabelController {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelService labelService;

    @GetMapping(value = "get/{type}")
    public List<LabelOutput> getLabels(@PathVariable LabelType type) {
        return labelMapper.getLabels(CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), type.toString());
    }

    @PutMapping(value = "get")
    public void updateOrInsertLabel(@Valid @RequestBody LabelOutput input) {
        labelService.updateOrInsertLabel(input);
    }

    @DeleteMapping(value = "get/{id}")
    public void deleteCategoryById(@PathVariable UUID id) {
        labelMapper.deleteLabelById(id.toString(), CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), Timestamp.from(ZonedDateTime.now().toInstant()));
    }

}
