package com.avenuecode.springbootrestjsonapp.repository;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    BookRepository bookRepository;

    BookEntity bookEntity = new BookEntity();

    BookEntity bookEntity1 = new BookEntity();

    BookEntity bookEntity2 = new BookEntity();

    @Before
    public void setup() {

    }

    void populateBookEntity() {
        bookEntity.setId(4L);
        bookEntity.setTitle("test1");
        bookEntity.setAuthor("author1");
        bookEntity.setPublishing("publishing1");

        bookEntity1.setId(4L);
        bookEntity1.setTitle("test1");
        bookEntity1.setAuthor("author1");
        bookEntity1.setPublishing("publishing1");

        bookEntity2.setId(5L);
        bookEntity2.setTitle("test2");
        bookEntity2.setAuthor("author2");
        bookEntity2.setPublishing("publishing2");

    }

    private void persistEntityManager() {
        entityManager.persist(bookEntity1);
        entityManager.persist(bookEntity2);
        entityManager.flush();
    }

    @Test //GET A DATA (BOOK)
    public void whenFindByName_thenReturnBookEntity(){
        //given
        populateBookEntity();
        persistEntityManager();

        //when
        Optional<BookEntity> foundBookEntity = bookRepository.findById(1L);

        //then
        Assertions.assertEquals(bookEntity.getAuthor(), foundBookEntity.get().getAuthor());
    }



    @Test //GET ALL DATA (BOOKS)
    public void whenFindAll_thenReturnAllBookEntities() {
        populateBookEntity();

        persistEntityManager();

        List<BookEntity> bookEntityListResult = bookRepository.findAll();

        Assertions.assertNotNull(bookEntityListResult);
        Assertions.assertEquals(bookEntity1.getAuthor(), bookEntityListResult.get(0).getAuthor());
    }

    @Test //SAVE
    public void whenSave_thenReturnBookEntity() {
        populateBookEntity();

        persistEntityManager();

        BookEntity savedBookEntity = bookRepository.save(bookEntity1);

        Assertions.assertNotNull(savedBookEntity);
        Assertions.assertEquals(bookEntity1.getTitle(), savedBookEntity.getTitle()) ;
    }

    @Test //UPDATE
    public void whenUpdate_thenReturnBookEntityUpdated() {
        populateBookEntity();

        persistEntityManager();

        Optional<BookEntity> savedValue = bookRepository.findById(4L);

        savedValue.get().setAuthor(bookEntity2.getAuthor());
        bookRepository.save(savedValue.get());

        Assertions.assertNotNull(savedValue);
        Assertions.assertEquals(bookEntity2.getAuthor(), savedValue.get().getAuthor());
    }

    @Test //DELETE
    public void whenDelete_ThenReturnNothing() {
        populateBookEntity();

        persistEntityManager();

        Optional<BookEntity> bookEntityFound = bookRepository.findById(4L);

        bookRepository.deleteById(bookEntityFound.get().getId());

        Assertions.assertEquals(false , entityManager.getEntityManager().contains(bookEntity1));
        Assertions.assertEquals(true , entityManager.getEntityManager().contains(bookEntity2));
    }
}
