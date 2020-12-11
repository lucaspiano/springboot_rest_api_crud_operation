package com.avenuecode.springbootrestjsonapp.service.impl;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceImplIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    BookEntity bookEntity_fromDB = new BookEntity();

    List<BookEntity> bookEntityList = new ArrayList<>();

    private void populateEntity() {
        bookEntity1.setId(4L);
        bookEntity1.setTitle("test1");
        bookEntity1.setAuthor("author1");
        bookEntity1.setPublishing("publishing1");

        bookEntity2.setId(5L);
        bookEntity2.setTitle("test2");
        bookEntity2.setAuthor("author2");
        bookEntity2.setPublishing("publishing2");
    }

    private void addEntityToList() {
        bookEntityList.add(bookEntity1);
        bookEntityList.add(bookEntity2);
    }

    @Test //POST
    public void givenBookDTO_whenCallCreate_thenReturnBookEntitySaved() {
        //given
        populateEntity();

        //when
        BookEntity bookEntitySaved = bookService.create(bookEntity1);

        //then
        Assertions.assertNotNull(bookEntitySaved);
        Assertions.assertEquals(bookEntity1.getAuthor(), bookEntitySaved.getAuthor());
    }

    @Test //GET ALL BOOKS
    public void givenRequest_whenCallingGetAllBooks_ThenReturnListBooksEntity() {
        //given
        populateEntity();
        addEntityToList();

        //when
        List<BookEntity> bookEntityListResult = bookService.findAll();

        //then
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
        Optional<BookEntity> bookEntityOptional = bookService.update(id,bookEntity_DTO);

        //then
        Assert.assertNotNull(bookEntityOptional);
        Assert.assertEquals(true, bookEntityOptional.get().getAuthor().equals("author01"));
    }

    @Test //DELETE BY ID
    public void givenId_whenCallingDelete_thenDeleteBookEntityById_ReturnsNothing() {

        //given
        Long id = 1L;

        //when
        bookService.delete(id);

        //then
        Assert.assertEquals(false, bookService.findById(1L).isPresent());
    }
}

