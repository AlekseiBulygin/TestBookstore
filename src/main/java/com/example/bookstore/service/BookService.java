package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO save(BookDTO newBook);

    BookDTO findByName(String name);

    BookDTO findById(Long id);

    List<BookDTO> findAll();

    List<BookDTO> findByRackId(Long id);

    List<BookDTO> findByShelfLevel(Long shelfLevel);

    List<BookDTO> findByRackIdAndShelfLevel(Long rackId, Long shelfLevel);

    BookDTO replaceBook(BookDTO newBook, Long id);

    void deleteById(Long id);

}
