package com.avenuecode.springbootrestjsonapp.controller.impl;

import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class )
class BookControllerImplUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookService bookService;

    @Autowired
    BookControllerImpl bookController;

    @Autowired
    private MockMvc mvc;

    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    BookDTO bookDTO = new BookDTO();

    List<BookEntity> entityList = new ArrayList<>();

    String updatedBookJson = "{\"title\":\"titleUpdated\",\"author\":\"authorUpdated\",\"publishing\":\"publishingUpdated\"}";

    String savedBookJson = "{\"title\":\"title01\",\"author\":\"author01\",\"publishing\":\"publishing01\"}";

    //starts the mocks and populate the beans
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        bookEntity1.setId(1L);
        bookEntity1.setAuthor("author1");
        bookEntity1.setTitle("title1");
        bookEntity1.setPublishing("publishing1");

        bookEntity2.setId(2L);
        bookEntity2.setAuthor("Lucas02");
        bookEntity2.setTitle("LucasTitle02");
        bookEntity2.setPublishing("LucasPublishing02");

        bookDTO.setId(1L);
        bookDTO.setTitle("title4");
        bookDTO.setAuthor("author4");
        bookDTO.setPublish("publish4");

        entityList.add(bookEntity1);
        entityList.add(bookEntity2);
    }

    //@POST
    @Test
    void givenBookDTO_whenCallingControllerCreate_thenReturnSavedBookDTO() throws Exception{
       when(bookService.create(bookEntity1)).thenReturn(bookEntity1);

       BookEntity bookEntityResult = bookService.create(bookEntity1);

       RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .accept(MediaType.APPLICATION_JSON.APPLICATION_JSON).content(savedBookJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/",
                response.getHeader(HttpHeaders.LOCATION));
    }

    //@get("/books")
    @Test
    void givenPostRequest_whenCallingControllerFindAll_thenReturnAllSavedBookEntities() {
        when(bookService.findAll()).thenReturn(entityList);

        List<BookDTO> resultList = bookController.getAllBooks();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals("author1", resultList.get(0).getAuthor());
    }

    //@get("/books/{id}")
    @Test
    void givenBookDTO_whenCallingControllerFindIdBy_thenReturnBookDTO() throws Exception {
        Long id = 1L;

        when(bookService.findById(id)).thenReturn(Optional.ofNullable(bookEntity1));

        bookController.getBook(id);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/books/1")
                .accept(MediaType.APPLICATION_JSON.APPLICATION_JSON).content(savedBookJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test //method to check if the mocks are working properly (ie - BookController)
    void testShow() {
        String value = "Lucas";

        String result = bookController.show(value);

        Assertions.assertEquals(value, result);
    }

    //@update("/books/{id}")
    @Test
    void givenBookDTO_whenCallingControllerUpdate_thenReturnNewSavedBookDTO() throws Exception {
        Long id = 1L;
        when(bookService.update(id,bookEntity1)).thenReturn(Optional.ofNullable(bookEntity1));

        bookController.update(id, bookDTO);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/books/1")
                .accept(MediaType.APPLICATION_JSON.APPLICATION_JSON).content(updatedBookJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
    }

    //@delete("/books/{id}")
    @Test
    void givenId_whenCallingDelete_thenBookEntityGetsDeleted() throws Exception {
        long id = 1L;
        bookService.delete(id);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/books/1")
                .accept(MediaType.APPLICATION_JSON.APPLICATION_JSON).content(savedBookJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(204, response.getStatus());
    }
}