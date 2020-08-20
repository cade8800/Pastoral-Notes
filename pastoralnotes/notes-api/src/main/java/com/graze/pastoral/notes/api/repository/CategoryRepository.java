package com.graze.pastoral.notes.api.repository;

import com.graze.pastoral.notes.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:54
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
}
