package com.example.bookstore.controller;

import com.example.bookstore.exceptions.*;
import com.example.bookstore.objects.*;
import com.example.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookstoreController {
    private final BookService bookService;

    BookstoreController (BookService repository) {
        this.bookService = repository;
    }

    @GetMapping("/books")
    Iterable<BookEntity> all() {
        return bookService.findAll();
    }

    @GetMapping("/books/rack/{id}")
    Iterable<BookEntity> byRackId(@PathVariable Long id) {
        return bookService.findByRackId(id);
    }

    @GetMapping("/books/shelf/{id}")
    Iterable<BookEntity> byShelfId(@PathVariable Long id) {
        return bookService.findByShelfId(id);
    }

    @GetMapping("/books/rack_shelf/{rackId}/{shelfId}")
    Iterable<BookEntity> byRackIdAndShelfId(@PathVariable Long rackId, @PathVariable Long shelfId) {
        return bookService.findByRackIdAndShelfId(rackId, shelfId);
    }

    @PostMapping(value = "/addBook", consumes = {"application/json"})
    BookEntity newBook(@RequestBody BookEntity newBook) {
        return bookService.save(newBook);
    }

    @GetMapping("/books/{id}")
    BookEntity one(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/books/name/{name}")
    BookEntity oneByName(@PathVariable String name) {
        return bookService.findByName(name);
    }

    @PutMapping("/books/{id}")
    BookEntity replaceBook(@RequestBody BookEntity newBook, @PathVariable Long id) {
        return bookService.replaceBook(newBook, id);
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
