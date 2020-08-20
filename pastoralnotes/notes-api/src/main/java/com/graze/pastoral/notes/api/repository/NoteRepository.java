package com.graze.pastoral.notes.api.repository;

import com.graze.pastoral.notes.domain.dto.note.NoteOutput;
import com.graze.pastoral.notes.domain.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:53
 */
public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

}
