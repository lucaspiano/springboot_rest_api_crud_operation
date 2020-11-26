package com.avenuecode.springbootrestjsonapp.controller.impl;

import com.avenuecode.springbootrestjsonapp.controller.BookController;
import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookControllerImpl implements BookController {

    BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    public List<BookDTO> convertEntityListToDTOList(List<BookEntity> listBookEntity) {
        List<BookDTO> listBookDTO = new ArrayList<>();
        for (BookEntity bookEntity: listBookEntity) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(bookEntity.getId());
            bookDTO.setTitle(bookEntity.getTitle());
            bookDTO.setAuthor(bookEntity.getAuthor());
            bookDTO.setPublish(bookEntity.getPublishing());
            listBookDTO.add(bookDTO);
        }
        return listBookDTO;
    }

    public BookDTO convertEntityToDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setPublish(bookEntity.getPublishing());
        return bookDTO;
    }

    public BookEntity convertDTOToEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublishing(bookDTO.getPublishing());
        return bookEntity;
    }

    //just to check if this method will be reached by the tests
    @GetMapping(value="/lucas")
    public String show(String value) {
        return "Lucas";
    }

    @Override
    @GetMapping(value="/books")
    public List<BookDTO> getAllBooks() {
        List<BookEntity> listBookEntity =  bookService.findAll();
        List<BookDTO> listBookDTO = convertEntityListToDTOList(listBookEntity);

        return listBookDTO;
    }

    @Override
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        BookEntity savedBookEntity = bookService.findById(id).orElseThrow(() -> new ResourceNotFoundException("" +
                "Book not found for this id " + id));
        BookDTO bookDTO = convertEntityToDTO(savedBookEntity);
        return ResponseEntity.ok().body(bookDTO);
    }


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = convertDTOToEntity(bookDTO);
        BookEntity savedBookEntity = bookService.create(bookEntity);
        BookDTO createdBookDTO = convertEntityToDTO(savedBookEntity);
        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookDTO.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(createdBookDTO);
    }


    @PutMapping("books/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody BookDTO bookDTO) throws ResourceNotFoundException {
        BookEntity bookEntity = convertDTOToEntity(bookDTO);
        Optional<BookEntity> entity = bookService.update(id,bookEntity);
        BookDTO bookDTOTransformed = convertEntityToDTO(entity.get());
        return ResponseEntity.ok(bookDTOTransformed);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* example of json data for this exercise
    {
	"title": "Book4",
	"author": "author4",
	"publishing": "publishing4"
    }
     */
}
