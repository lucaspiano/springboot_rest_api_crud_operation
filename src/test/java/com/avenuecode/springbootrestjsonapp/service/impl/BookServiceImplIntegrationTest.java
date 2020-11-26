package com.avenuecode.springbootrestjsonapp.service.impl;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplIntegrationTest {

    /*
    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {

        @Autowired
        BookRepository bookRepository;

        @Bean
        public BookService bookService() {
            return new BookServiceImpl(bookRepository);
        }
    }

     */

    private BookRepository bookRepository = mock(BookRepository.class);

    private BookService bookService = new BookServiceImpl(bookRepository);

    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    BookEntity bookEntity_fromDB = new BookEntity();

    List<BookEntity> bookEntityList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        bookEntity1.setId(4L);
        bookEntity1.setTitle("test1");
        bookEntity1.setAuthor("author1");
        bookEntity1.setPublishing("publishing1");

        bookEntity2.setId(5L);
        bookEntity2.setTitle("test2");
        bookEntity2.setAuthor("author2");
        bookEntity2.setPublishing("publishing2");
    }

    @Test //POST
    public void givenBookDTO_whenCallCreate_thenReturnBookEntitySaved() {
        when(bookRepository.save(bookEntity1)).thenReturn(bookEntity1);

        BookEntity bookEntitySaved = bookService.create(bookEntity1);

        Assertions.assertNotNull(bookEntitySaved);
        Assertions.assertEquals(bookEntity1.getAuthor(), bookEntitySaved.getAuthor());
    }

    @Test //GET ALL BOOKS
    public void givenRequest_whenCallingGetAllBooks_ThenReturnListBooksEntity() {
        when(bookRepository.findAll()).thenReturn(bookEntityList);

        bookEntityList.add(bookEntity1);
        bookEntityList.add(bookEntity2);

        List<BookEntity> bookEntityListResult = bookService.findAll();

        Assertions.assertNotNull(bookEntityListResult);
        Assertions.assertEquals(bookEntityList.get(0).getAuthor(), bookEntityListResult.get(0).getAuthor());
    }

    @Test //UPDATE BY ID
    public void givenId_whenCallingUpdate_ThenReturnUpdatedBookEntity() {

        //given
        Long id = 1L;

        BookEntity bookEntity_DTO = new BookEntity();
        bookEntity_DTO.setId(id);
        bookEntity_DTO.setTitle("title01");
        bookEntity_DTO.setAuthor("author01");
        bookEntity_DTO.setPublishing("publishing01");

        bookEntity_fromDB.setId(id);
        bookEntity_fromDB.setTitle("title02");
        bookEntity_fromDB.setAuthor("author02");
        bookEntity_fromDB.setPublishing("publishing02");

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity_fromDB));
        when(bookRepository.save(bookEntity_DTO)).thenReturn(bookEntity_DTO);

        Optional<BookEntity> bookEntityOptional = bookService.update(id,bookEntity_DTO);

        //then
        Assert.assertNotNull(bookEntityOptional);
        Assert.assertEquals(true, bookEntityOptional.get().getAuthor().equals("author01"));
    }

    @Test //DELETE BY ID
    public void givenId_whenCallingDelete_thenDeleteBookEntityById_ReturnsNothing() {

        //given
        Long id = 1L;

        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setId(id);
        bookEntity1.setTitle("title01");
        bookEntity1.setAuthor("author01");
        bookEntity1.setPublishing("publishing01");

        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setId(2L);
        bookEntity2.setTitle("title02");
        bookEntity2.setAuthor("author02");
        bookEntity2.setPublishing("publishing02");

        List<BookEntity> resultBookEntity = new ArrayList<>();
        resultBookEntity.add(bookEntity1);
        resultBookEntity.add(bookEntity2);

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(resultBookEntity.remove(0)));

        bookService.delete(id);

        //then
        Assert.assertEquals(1, resultBookEntity.size());
        Assert.assertEquals(false, resultBookEntity.contains(bookEntity1));
        Assert.assertEquals(true, resultBookEntity.contains(bookEntity2));
    }
}

