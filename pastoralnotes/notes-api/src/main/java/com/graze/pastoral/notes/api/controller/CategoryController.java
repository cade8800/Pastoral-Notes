package com.graze.pastoral.notes.api.controller;

import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.CategoryMapper;
import com.graze.pastoral.notes.api.service.CategoryService;
import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Delete;
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
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "get")
    public List<CategoryOutput> getCategories() {
        return categoryMapper.getCategories(CurrentUserUtil.getCurrentLoginUser().getUserId().toString());
    }

    @PutMapping(value = "get")
    public void updateOrInsertCategory(@Valid @RequestBody CategoryOutput input) {
        categoryService.updateOrInsertCategory(input);
    }

    @DeleteMapping(value = "get/{id}")
    public void deleteCategoryById(@PathVariable UUID id) {
        categoryMapper.deleteCategoryById(id.toString(), CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), Timestamp.from(ZonedDateTime.now().toInstant()));
    }

}
