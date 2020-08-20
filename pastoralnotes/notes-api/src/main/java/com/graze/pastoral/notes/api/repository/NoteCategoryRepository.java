package com.graze.pastoral.notes.api.repository;

import com.graze.pastoral.notes.domain.entity.NoteCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/24 15:42
 */
public interface NoteCategoryRepository extends JpaRepository<NoteCategoryEntity, UUID> {
}
