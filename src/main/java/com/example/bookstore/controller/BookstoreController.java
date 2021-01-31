package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/bookstore")
@Validated
public class BookstoreController {
    private final BookService bookService;

    BookstoreController (BookService service) {
        this.bookService = service;
    }

    @GetMapping
    ResponseEntity<List<BookDTO>> all() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping(value = "/books", params = "rackId")
    ResponseEntity<List<BookDTO>> byRackId(@RequestParam Long rackId) {
        return ResponseEntity.ok(bookService.findByRackId(rackId));
    }

    @GetMapping(value = "/books", params = "shelfLevel")
    ResponseEntity<List<BookDTO>> byShelfLevel(@RequestParam @Max(3L) @Min(1L) Long shelfLevel) {
        return ResponseEntity.ok(bookService.findByShelfLevel(shelfLevel));
    }

    @GetMapping(value = "/books", params = {"rackId", "shelfLevel"})
    ResponseEntity<List<BookDTO>> byRackIdAndShelfId(@RequestParam Long rackId,
            @RequestParam @Max(3L) @Min(1L) Long shelfLevel) {
        return ResponseEntity.ok(bookService.findByRackIdAndShelfLevel(rackId, shelfLevel));
    }

    @PostMapping(value = "/books", consumes = {"application/json"})
    ResponseEntity<BookDTO> newBook(@RequestBody @Valid BookDTO newBook) {
        return ResponseEntity.ok(bookService.save(newBook));
    }

    @GetMapping(value = "/books", params = "id")
    ResponseEntity<BookDTO> one(@RequestParam Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping(value = "/books", params = "name")
    ResponseEntity<BookDTO> oneByName(@RequestParam String name) {
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
