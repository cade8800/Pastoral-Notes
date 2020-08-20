package com.graze.pastoral.notes.api.service;

import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.CategoryMapper;
import com.graze.pastoral.notes.api.repository.CategoryRepository;
import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import com.graze.pastoral.notes.domain.entity.CategoryEntity;
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
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void updateOrInsertCategory(CategoryOutput input) {
        if (input.getId() == null) {
            checkCategoryIsExist(input.getName());
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(input.getName());
            categoryEntity.setCreateUserId(CurrentUserUtil.getCurrentLoginUser().getUserId());
            categoryRepository.save(categoryEntity);
        } else {

            String oldCategoryName = categoryMapper.getCategoryNameById(input.getId().toString());

            if (StringUtils.isEmpty(oldCategoryName))
                throw new RuntimeException("类别不存在");

            if (!oldCategoryName.equals(input.getName())) {

                checkCategoryIsExist(input.getName());

                categoryMapper.updateCategory(
                        input.getId().toString(),
                        input.getName(),
                        CurrentUserUtil.getCurrentLoginUser().getUserId().toString(),
                        Timestamp.from(ZonedDateTime.now().toInstant())
                );
            }
        }
    }

    private void checkCategoryIsExist(String categoryName) {
        Integer count = categoryMapper.countCategoriesByName(categoryName, CurrentUserUtil.getCurrentLoginUser().getUserId().toString());
        if (count > 0) throw new RuntimeException("类别[" + categoryName + "]已存在");
    }
}
