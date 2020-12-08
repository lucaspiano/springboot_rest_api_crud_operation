package com.avenuecode.springbootrestjsonapp.controller.impl;

import com.avenuecode.springbootrestjsonapp.controller.BookController;
import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import com.avenuecode.springbootrestjsonapp.utils.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BookControllerImpl implements BookController {

    BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
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
        List<BookDTO> listBookDTO = ConverterUtils.convertEntityListToDTOList(listBookEntity);

        return listBookDTO;
    }

    @Override
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        BookEntity savedBookEntity = bookService.findById(id).orElseThrow(() -> new ResourceNotFoundException("" +
                "Book not found for this id " + id));
        BookDTO bookDTO = ConverterUtils.convertEntityToDTO(savedBookEntity);
        return ResponseEntity.ok().body(bookDTO);
    }


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = ConverterUtils.convertDTOToEntity(bookDTO);
        BookEntity savedBookEntity = bookService.create(bookEntity);
        BookDTO createdBookDTO = ConverterUtils.convertEntityToDTO(savedBookEntity);
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
        BookEntity bookEntity = ConverterUtils.convertDTOToEntity(bookDTO);
        Optional<BookEntity> entity = bookService.update(id,bookEntity);
        BookDTO bookDTOTransformed = ConverterUtils.convertEntityToDTO(entity.get());
        return ResponseEntity.ok(bookDTOTransformed);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
