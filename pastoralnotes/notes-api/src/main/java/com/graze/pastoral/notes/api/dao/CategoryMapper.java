package com.graze.pastoral.notes.api.dao;

import com.graze.pastoral.notes.domain.dto.category.CategoryOutput;
import com.graze.pastoral.notes.domain.dto.note.NotesDetailOutput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/24 15:20
 */
@Repository
public interface CategoryMapper {
    List<CategoryOutput> getCategories(String createUserId);

    List<String> getNotesCategoryIds(String noteId);

    List<CategoryOutput> getNoteCategories(String noteId);

    void updateCategory(String categoryId, String name, String updateUserId, Timestamp updateTime);

    Integer countCategoriesByName(String name, String createUserId);

    String getCategoryNameById(String categoryId);

    void deleteCategoryById(String categoryId, String createUserId, Timestamp updateTime);
}
