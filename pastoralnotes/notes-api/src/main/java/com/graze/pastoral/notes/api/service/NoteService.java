package com.graze.pastoral.notes.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.graze.pastoral.notes.api.config.CurrentUserUtil;
import com.graze.pastoral.notes.api.dao.*;
import com.graze.pastoral.notes.api.repository.NoteCategoryRepository;
import com.graze.pastoral.notes.api.repository.NoteLabelRepository;
import com.graze.pastoral.notes.api.repository.NoteRepository;
import com.graze.pastoral.notes.domain.dto.baseDto.BasePagingOutput;
import com.graze.pastoral.notes.domain.dto.note.GetNotesInput;
import com.graze.pastoral.notes.domain.dto.note.NoteOutput;
import com.graze.pastoral.notes.domain.dto.note.NotesDetailOutput;
import com.graze.pastoral.notes.domain.dto.note.UpdateNoteDetailInput;
import com.graze.pastoral.notes.domain.entity.*;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

//import org.springframework.data.domain.Page;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 11:56
 */
@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private NoteLabelRepository noteLabelRepository;

    @Autowired
    private NoteLabelMapper noteLabelMapper;

    @Autowired
    private NoteCategoryMapper noteCategoryMapper;

    @Autowired
    private NoteCategoryRepository noteCategoryRepository;


    public BasePagingOutput<NoteOutput> getNotes(GetNotesInput input) {

        Page<NoteOutput> notePage = PageHelper.startPage(input.getPageIndex(), input.getPageSize(), "create_time desc,update_time desc");
        noteMapper.getNotes(
                CurrentUserUtil.getCurrentLoginUser().getUserId().toString(),
                StringUtils.isBlank(input.getKeyword()) ? null : input.getKeyword().trim(),
                input.getLabelId() == null ? "" : input.getLabelId().toString(),
                input.getCategoryId() == null ? "" : input.getCategoryId().toString(),
                input.getHasNoLabels(),
                input.getHasNoCategories()
        );

        BasePagingOutput<NoteOutput> output = new BasePagingOutput<>();
        output.setTotalElements(notePage.getTotal());
        output.setTotalPages(notePage.getPages());
        output.setNumberOfElements(notePage.getPageSize());
        output.setPageNumber(notePage.getPageNum());

        List<NoteOutput> outputList = notePage.getResult();
        outputList.forEach(t -> {
            if (t.getContent().length() > 150) {
                t.setContent(t.getContent().substring(0, 150));
            }
        });

        output.setContent(outputList);

//        ModelMapper modelMapper = new ModelMapper();
//        Type targetType = new TypeToken<List<NoteOutput>>() {
//        }.getType();
//        output.setContent(modelMapper.map(notePage.getResult(), targetType));

        return output;
    }

    public NotesDetailOutput getNoteDetail(UUID id) {
        NotesDetailOutput output = noteMapper.getNotesDetail(
                id.toString(),
                CurrentUserUtil.getCurrentLoginUser().getUserId().toString()
        );

        output.setCategories(
                categoryMapper.getNoteCategories(id.toString())
        );

        output.setLabels(
                labelMapper.getNoteLabels(id.toString())
        );

        return output;
    }

    @Transactional
    public void updateOrInsertNote(UpdateNoteDetailInput input) {
        UUID currentUserId = CurrentUserUtil.getCurrentLoginUser().getUserId();

        if (input.getId() == null) {
            NoteEntity note = new NoteEntity(
                    input.getTitle(),
                    input.getSummary(),
                    input.getCoverUrl(),
                    input.getContent(),
                    currentUserId
            );
            noteRepository.save(note);

            input.getCategories().forEach(c -> {
                CategoryEntity category = new CategoryEntity();
                category.setId(c);
                NoteCategoryEntity nc = new NoteCategoryEntity(note, category, currentUserId);
                noteCategoryRepository.save(nc);
            });

            input.getLabels().forEach(l -> {
                LabelEntity label = new LabelEntity();
                label.setId(l);
                NoteLabelEntity nl = new NoteLabelEntity(note, label, currentUserId);
                noteLabelRepository.save(nl);
            });


        } else {

            noteMapper.updateNote(input.getId().toString(),
                    input.getTitle(),
                    input.getSummary(),
                    input.getCoverUrl(),
                    input.getContent(),
                    currentUserId.toString(),
                    Timestamp.from(ZonedDateTime.now().toInstant())
            );

            noteCategoryMapper.deleteNoteCategoriesByNoteId(input.getId().toString(), currentUserId.toString());
            for (UUID categoryId : input.getCategories()) {
                NoteEntity n = new NoteEntity();
                n.setId(input.getId());
                CategoryEntity c = new CategoryEntity();
                c.setId(categoryId);
                NoteCategoryEntity nc = new NoteCategoryEntity();
                nc.setCategory(c);
                nc.setNote(n);
                nc.setCreateUserId(currentUserId);
                noteCategoryRepository.save(nc);
            }

            noteLabelMapper.deleteNoteLabelsByNoteId(input.getId().toString(), currentUserId.toString());
            for (UUID labelId : input.getLabels()) {
                NoteEntity n = new NoteEntity();
                n.setId(input.getId());
                LabelEntity l = new LabelEntity();
                l.setId(labelId);
                NoteLabelEntity nl = new NoteLabelEntity();
                nl.setLabel(l);
                nl.setNote(n);
                nl.setCreateUserId(currentUserId);
                noteLabelRepository.save(nl);
            }
        }
    }

    public void deleteNoteById(UUID noteId) {
        noteMapper.deleteNoteById(noteId.toString(), CurrentUserUtil.getCurrentLoginUser().getUserId().toString(), Timestamp.from(ZonedDateTime.now().toInstant()));
    }
}
