package com.graze.pastoral.notes.api.repository;

import com.graze.pastoral.notes.domain.entity.NoteLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:53
 */
public interface NoteLabelRepository extends JpaRepository<NoteLabelEntity, UUID> {

}
