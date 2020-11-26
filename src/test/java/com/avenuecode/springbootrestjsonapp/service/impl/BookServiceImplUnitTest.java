package com.avenuecode.springbootrestjsonapp.service.impl;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceImplUnitTest {

    private BookRepository bookRepository = Mockito.mock(BookRepository.class);

    private BookService bookService = new BookServiceImpl(bookRepository);

    BookEntity bookEntity = new BookEntity();

    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    List<BookEntity> resultBookEntity = new ArrayList<>();

    BookEntity bookEntity_DTO = new BookEntity();

    BookEntity bookEntity_fromDB = new BookEntity();

    private Long id = 1L;

    @Before
    public void setup() throws Exception {

    }

    private void populateBean() {
        //used on post
        bookEntity.setId(1L);
        bookEntity.setTitle("title01");
        bookEntity.setAuthor("author01");
        bookEntity.setPublishing("publishing01");

        //used on consult(get), update and delete
        bookEntity1.setId(1L);
        bookEntity1.setTitle("title01");
        bookEntity1.setAuthor("author01");
        bookEntity1.setPublishing("publishing01");

        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setId(2L);
        bookEntity2.setTitle("title02");
        bookEntity2.setAuthor("author02");
        bookEntity2.setPublishing("publishing02");

        resultBookEntity.add(bookEntity1);
        resultBookEntity.add(bookEntity2);
    }

    private void populateBeanUpdate() {
        bookEntity_DTO.setId(id);
        bookEntity_DTO.setTitle("title01");
        bookEntity_DTO.setAuthor("author01");
        bookEntity_DTO.setPublishing("publishing01");


        bookEntity_fromDB.setId(id);
        bookEntity_fromDB.setTitle("title02");
        bookEntity_fromDB.setAuthor("author02");
        bookEntity_fromDB.setPublishing("publishing02");
    }

    //@POST - create()
    @Test
    void givenBookEntity_whenCallsCreate_thenReturnBookEntityWithIdAfterSave() {
        //given
        populateBean();

        //when
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        BookEntity savedBookEntity = bookService.create(bookEntity);

        //then
        Assert.assertNotNull(savedBookEntity);
        Assert.assertNotNull(savedBookEntity.getAuthor());
        Assert.assertEquals("author01", savedBookEntity.getAuthor());
    }

    //@GET - getAllBooks()
    @Test
    void givenNoParameters_getAllBooks_whenCallingFindAll_thenBookRepositoryAllBooksAreShowTest() {
        //given
        populateBean();

        //when
        when(bookRepository.save(bookEntity1)).thenReturn(bookEntity1);
        when(bookRepository.save(bookEntity2)).thenReturn(bookEntity2);
        when(bookRepository.findAll()).thenReturn(resultBookEntity);

        List<BookEntity> bookEntityList = bookService.findAll();

        //then
        Assert.assertNotNull(bookEntityList);
        Assert.assertEquals("title01", bookEntityList.get(0).getTitle());
        Assert.assertEquals("author02", bookEntityList.get(1).getAuthor());
    }

    //@GET(id) - findById(id)
    @Test
    void givenIDBookEntity_whenCallingBookServiceFindById_thenBookRepositoryByIdReturnsBookEntity() {
        //given
        populateBean();

        //when
        when(bookRepository.findById(bookEntity1.getId())).thenReturn(Optional.of(bookEntity1));

        Optional<BookEntity> bookEntityOptional = bookService.findById(bookEntity1.getId());

        //then
        Assert.assertNotNull(bookEntityOptional);
        Assert.assertEquals(bookEntity1.getAuthor(), bookEntityOptional.get().getAuthor());

    }

    //@UPDATE - update(id)
    @Test
    void givenIDBookEntity_whenCallingBookServiceUpdate_thenBookRepositoryUpdateByIdTest() {
        //given
        populateBeanUpdate();

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity_fromDB));
        when(bookRepository.save(bookEntity_DTO)).thenReturn(bookEntity_DTO);

        Optional<BookEntity> bookEntityOptional = bookService.update(id,bookEntity_DTO);

        //then
        Assert.assertNotNull(bookEntityOptional);
        Assert.assertEquals(true, bookEntityOptional.get().getAuthor().equals("author01"));
    }

    //@DELETE(id) - delete(id)
    @Test
    void givenIdBookEntity_whenCallingBookServiceDelete_thenBookRepositoryDeleteTest() {
        //given
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
