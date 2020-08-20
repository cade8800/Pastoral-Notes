package com.graze.pastoral.notes.api.dao;

import com.graze.pastoral.notes.domain.dto.note.NoteOutput;
import com.graze.pastoral.notes.domain.dto.note.NotesDetailOutput;
import com.graze.pastoral.notes.domain.entity.NoteEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface NoteMapper {
    List<NoteOutput> getNotes(String createUserId, String keyword, String labelId, String categoryId, Boolean hasNoLabels, Boolean hasNoCategories);

    NotesDetailOutput getNotesDetail(String noteId, String createUserId);

    void updateNote(String noteId, String title, String summary, String coverUrl, String content, String currentUserId, Timestamp updateTime);

    void deleteNoteById(String noteId, String currentUserId, Timestamp updateTime);
}
