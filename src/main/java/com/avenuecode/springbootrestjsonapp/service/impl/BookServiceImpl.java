package com.avenuecode.springbootrestjsonapp.service.impl;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import com.avenuecode.springbootrestjsonapp.repository.BookRepository;
import com.avenuecode.springbootrestjsonapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookEntity> findAll() {
        List<BookEntity> listService = bookRepository.findAll();

        return listService;
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        //if (id != null && bookRepository.existsById(id)) {
            Optional<BookEntity> valueFoundById = bookRepository.findById(id);
            return valueFoundById;
        //}

        //return Optional.empty();
    }

    public BookEntity create(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    public void delete(Long id) {
        if (id != null) {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public Optional<BookEntity> update(Long id, BookEntity bookEntity) {
        if (id != null) {
            Optional<BookEntity> savedBookEntity = bookRepository.findById(id);
            savedBookEntity.get().setTitle(bookEntity.getTitle());
            savedBookEntity.get().setAuthor(bookEntity.getAuthor());
            savedBookEntity.get().setPublishing(bookEntity.getPublishing());
            bookRepository.save(savedBookEntity.get());
            return savedBookEntity;
        }
        return Optional.empty();
    }
}
