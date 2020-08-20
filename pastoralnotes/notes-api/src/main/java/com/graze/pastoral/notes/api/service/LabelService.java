package com.graze.pastoral.notes.api.service;

import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.CategoryMapper;
import com.graze.pastoral.notes.api.dao.LabelMapper;
import com.graze.pastoral.notes.api.repository.CategoryRepository;
import com.graze.pastoral.notes.api.repository.LabelRepository;
import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import com.graze.pastoral.notes.domain.dto.label.LabelOutput;
import com.graze.pastoral.notes.domain.entity.CategoryEntity;
import com.graze.pastoral.notes.domain.entity.LabelEntity;
import com.graze.pastoral.notes.domain.type.LabelType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/30 15:43
 */
@Service
public class LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelRepository labelRepository;

    @Transactional
    public void updateOrInsertLabel(LabelOutput input) {
        LabelType type = LabelType.valueOf(input.getType());

        if (input.getId() == null) {
            checkLabelIsExist(input.getName(), type);
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setName(input.getName());
            labelEntity.setType(type);
            labelEntity.setCreateUserId(CurrentUserUtil.getCurrentLoginUser().getUserId());
            labelRepository.save(labelEntity);
        } else {

            String oldName = labelMapper.getLabelNameById(input.getId().toString());

            if (StringUtils.isEmpty(oldName))
                throw new RuntimeException("标签不存在");

            if (!oldName.equals(input.getName())) {

                checkLabelIsExist(input.getName(), type);

                labelMapper.updateLabel(
                        input.getId().toString(),
                        input.getName(),
                        CurrentUserUtil.getCurrentLoginUser().getUserId().toString(),
                        Timestamp.from(ZonedDateTime.now().toInstant())
                );
            }
        }
    }

    private void checkLabelIsExist(String categoryName, LabelType type) {
        Integer count = labelMapper.countLabelByName(categoryName, type.toString(), CurrentUserUtil.getCurrentLoginUser().getUserId().toString());
        if (count > 0) throw new RuntimeException("标签[" + categoryName + "]已存在");
    }
}
