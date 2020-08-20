package com.graze.pastoral.notes.api.dao;

import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/3 11:39
 */
@Repository
public interface NoteLabelMapper {
    void deleteNoteLabelsByNoteId(String id, String currentUserId);
}
