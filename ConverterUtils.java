package com.avenuecode.springbootrestjsonapp.utils;

import com.avenuecode.springbootrestjsonapp.domain.dto.BookDTO;
import com.avenuecode.springbootrestjsonapp.domain.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class ConverterUtils {
    public static List<BookDTO> convertEntityListToDTOList(List<BookEntity> listBookEntity) {
        List<BookDTO> listBookDTO = new ArrayList<>();
        /*
        for (BookEntity bookEntity: listBookEntity) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(bookEntity.getId());
            bookDTO.setTitle(bookEntity.getTitle());
            bookDTO.setAuthor(bookEntity.getAuthor());
            bookDTO.setPublish(bookEntity.getPublishing());
            listBookDTO.add(bookDTO);
        }

         */

        listBookEntity.forEach(list -> {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(list.getId());
            bookDTO.setTitle(list.getTitle());
            bookDTO.setAuthor(list.getAuthor());
            bookDTO.setPublish(list.getPublishing());
            listBookDTO.add(bookDTO);
        });
        return listBookDTO;
    }

    public static BookDTO convertEntityToDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setPublish(bookEntity.getPublishing());
        return bookDTO;
    }

    public static BookEntity convertDTOToEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookDTO.getId());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublishing(bookDTO.getPublishing());
        return bookEntity;
    }
}
