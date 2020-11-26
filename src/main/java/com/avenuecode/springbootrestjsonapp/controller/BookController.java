package com.avenuecode.springbootrestjsonapp.controller;

import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface BookController {
    List<BookDTO> getAllBooks();
    ResponseEntity<BookDTO> getBook(Long id) throws ResourceNotFoundException;
    ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO);
    ResponseEntity<BookDTO> update(@PathVariable(value = "id") Long id,
                                   @Valid @RequestBody BookDTO bookDTO) throws ResourceNotFoundException;
    ResponseEntity<Object> delete(@PathVariable Long id);
}
