package com.graze.pastoral.notes.api.controller;

import com.graze.pastoral.notes.api.service.NoteService;
import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingOutput;
import com.graze.pastoral.notes.domain.dto.note.GetNotesInput;
import com.graze.pastoral.notes.domain.dto.note.NoteOutput;
import com.graze.pastoral.notes.domain.dto.note.NotesDetailOutput;
import com.graze.pastoral.notes.domain.dto.note.UpdateNoteDetailInput;
import com.graze.pastoral.notes.domain.entity.NoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:58
 */
@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/get")
    public BasePagingOutput<NoteOutput> getNotes(@Valid @RequestBody GetNotesInput input) {
        return noteService.getNotes(input);
    }

    @GetMapping("/get/{id}")
    public NotesDetailOutput getNoteDetailById(@PathVariable UUID id) {
        return noteService.getNoteDetail(id);
    }

    @PutMapping("get")
    public void updateOrInsertNote(@Valid @RequestBody UpdateNoteDetailInput input) {
        noteService.updateOrInsertNote(input);
    }

    @DeleteMapping("get/{id}")
    public void deleteNoteById(@PathVariable UUID id) {
        noteService.deleteNoteById(id);
    }
}
