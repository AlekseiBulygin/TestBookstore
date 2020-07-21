package com.example.bookstore.service;

import com.example.bookstore.objects.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity save(BookEntity newBook);

    BookEntity findByName(String name);

    BookEntity findById(Long id);

    List<BookEntity> findAll();

    List<BookEntity> findByRackId(Long id);

    List<BookEntity> findByShelfId(Long id);

    List<BookEntity> findByRackIdAndShelfId(Long rackId, Long shelfId);

    BookEntity replaceBook(BookEntity newBook, Long id);

    void deleteById(Long id);

}
