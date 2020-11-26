package com.avenuecode.springbootrestjsonapp.repository;

import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {
    List<BookEntity> findAll();
    Optional<BookEntity> findById(Long id);
}
