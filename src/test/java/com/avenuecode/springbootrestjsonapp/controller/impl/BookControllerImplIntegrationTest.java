package com.avenuecode.springbootrestjsonapp.controller.impl;

import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerImplIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    List<BookEntity> bookEntityList = new ArrayList<>();


    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        bookEntity1.setId(4L);
        bookEntity1.setTitle("test1");
        bookEntity1.setAuthor("author1");
        bookEntity1.setPublishing("publishing1");

        bookEntity2.setId(5L);
        bookEntity2.setTitle("test2");
        bookEntity2.setAuthor("author2");
        bookEntity2.setPublishing("publishing2");

        bookEntityList.add(bookEntity1);
        bookEntityList.add(bookEntity2);
    }

    void populateBean(BookEntity bookEntity) {

    }

    @Test //it checks the @Configuration class
    public void givenWac_whenServletContext_thenItProvidesBookController() {
        ServletContext servletContext = wac.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(wac.getBean("JPAEnversConfiguration"));
    }

    @Test  //@GET("/books")
    public void givenBookEntity_whenGetAllBook_thenStatus200()
            throws Exception {

        mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test //@GET("/books/{id}")
    public void givenBookDTO_whenCallingControllerFindIdBy_thenStatus200()
            throws Exception {
        HttpEntity<BookDTO> entity = new HttpEntity<BookDTO>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/books/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"id\":1,\"title\":\"book1\",\"author\":\"author1\",\"publishing\":\"publishing1\"}";

        Assertions.assertEquals(expected, String.valueOf(response.getBody()));
    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    @Test //@POST("/")
    public void givenBookDTO_whenCallingControllerCreate_thenReturnLocationStatus201() {
        bookEntity1.setId(4L);
        bookEntity1.setTitle("test1");
        bookEntity1.setAuthor("author1");
        bookEntity1.setPublishing("publishing1");

        HttpEntity<BookEntity> entity = new HttpEntity<>(bookEntity1, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/"),
                HttpMethod.POST, entity, String.class);

        String actual = String.valueOf(response.getHeaders().get(HttpHeaders.LOCATION));
        Assert.assertTrue(actual.contains("/"));
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test //@UPDATE("/books/{id}")
    void givenBookDTO_whenCallingControllerUpdate_thenReturnStatus200() throws Exception {
        bookEntity1.setId(1L);
        bookEntity1.setTitle("test5");
        bookEntity1.setAuthor("author5");
        bookEntity1.setPublishing("publishing5");

        HttpEntity<BookEntity> entity = new HttpEntity<>(bookEntity1, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/books/1"),
                HttpMethod.PUT, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test //@DELETE("books/{id})
    void givenId_whenCallingDelete_thenReturnStatus204_NothingOnThePayload() throws Exception {
        bookEntity1.setId(1L);

        HttpEntity<BookEntity> entity = new HttpEntity<>(bookEntity1, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/books/1"),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(204, response.getStatusCodeValue());
    }

}
