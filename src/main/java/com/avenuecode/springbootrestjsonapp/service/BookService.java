package com.avenuecode.springbootrestjsonapp.service;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookEntity> findAll();
    Optional<BookEntity> findById(Long id);
    BookEntity create(BookEntity bookEntity);
    void delete(Long id);

    default Optional<BookEntity> update(Long id, BookEntity bookEntity) {
        return null;
    }
}
