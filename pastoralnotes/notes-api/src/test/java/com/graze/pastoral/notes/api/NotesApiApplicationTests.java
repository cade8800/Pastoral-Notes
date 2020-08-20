package com.graze.pastoral.notes.api;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/20 13:46
 */

@SpringBootTest
public class NotesApiApplicationTests {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void addAdmin() {
//        UserEntity user = new UserEntity();
//        user.setUsername("admin");
//        user.setPassword(new BCryptPasswordEncoder().encode("123"));
//        user.setUserType(UserType.SYSTEM_USER);
//        userRepository.save(user);
//    }

//    @Autowired
//    NoteRepository noteRepository;
//
//    @Test
//    public void addNote() {
//        for (int i = 0; i < 20; i++) {
//            NoteEntity noteEntity = new NoteEntity();
//            noteEntity.setContent("没错，这里就是内容了" + i);
//            noteEntity.setTitle("标题" + i);
//            noteEntity.setSummary("摘要" + i);
//            noteEntity.setCoverUrl("https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png");
//            noteEntity.setCreateUserId(UUID.fromString("c0a80fc8-736a-1ed9-8173-6adf01f70000"));
//            noteEntity.setUpdateUserId(UUID.fromString("c0a80fc8-736a-1ed9-8173-6adf01f70000"));
//            noteRepository.save(noteEntity);
//        }
//    }

//    @Test
//    public void testDate() {
//        Date d = new Date();
//        System.out.println(d.toString());
//    }


//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private NoteCategoryRepository noteCategoryRepository;
//
//    @Test
//    public void addCategories() {
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setName("丑陋分类");
//        categoryEntity.setCreateUserId(UUID.fromString("c0a80fc8-736a-1ed9-8173-6adf01f70000"));
//        categoryRepository.save(categoryEntity);
//
//        NoteCategoryEntity noteCategoryEntity = new NoteCategoryEntity();
//        noteCategoryEntity.setCategory(categoryEntity);
//
//        NoteEntity noteEntity = new NoteEntity();
//        noteEntity.setId(UUID.fromString("c0a80067-7376-1d36-8173-766d530d0010"));
//
//        noteCategoryEntity.setNote(noteEntity);
//        noteCategoryRepository.save(noteCategoryEntity);
//    }
}

