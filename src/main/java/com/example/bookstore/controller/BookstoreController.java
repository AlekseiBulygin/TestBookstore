package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookstoreController {
    private final BookService bookService;

    BookstoreController (BookService service) {
        this.bookService = service;
    }

    @GetMapping("/books")
    ResponseEntity<List<BookDTO>> all() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/books/rack/{id}")
    ResponseEntity<List<BookDTO>> byRackId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findByRackId(id));
    }

    @GetMapping("/books/shelf/{id}")
    ResponseEntity<List<BookDTO>> byShelfId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findByShelfId(id));
    }

    @GetMapping("/books/rack_shelf/{rackId}/{shelfId}")
    ResponseEntity<List<BookDTO>> byRackIdAndShelfId(@PathVariable Long rackId, @PathVariable Long shelfId) {
        return ResponseEntity.ok(bookService.findByRackIdAndShelfId(rackId, shelfId));
    }

    @PostMapping(value = "/addBook", consumes = {"application/json"})
    ResponseEntity<BookDTO> newBook(@RequestBody BookDTO newBook) {
        return ResponseEntity.ok(bookService.save(newBook));
    }

    @GetMapping("/books/{id}")
    ResponseEntity<BookDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/books/name/{name}")
    ResponseEntity<BookDTO> oneByName(@PathVariable String name) {
        return ResponseEntity.ok(bookService.findByName(name));
    }

    @PutMapping("/books/{id}")
    ResponseEntity<BookDTO> replaceBook(@RequestBody BookDTO newBook, @PathVariable Long id) {
        return ResponseEntity.ok(bookService.replaceBook(newBook, id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
