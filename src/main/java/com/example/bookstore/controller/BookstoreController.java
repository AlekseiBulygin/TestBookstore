package com.example.bookstore.controller;

import com.example.bookstore.exceptions.*;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.BookRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookstoreController {
    private final BookRepository repository;

    BookstoreController (BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    Iterable<BookEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/books/rack/{id}")
    Iterable<BookEntity> byRackId(@PathVariable Long id) {
        return repository.findByRackId(id.toString());
    }

    @GetMapping("/books/shelf/{id}")
    Iterable<BookEntity> byShelfId(@PathVariable Long id) {
        return repository.findByShelfId(id);
    }

    @GetMapping("/books/rack_shelf/{rackId}/{shelfId}")
    Iterable<BookEntity> byRackIdAndShelfId(@PathVariable Long rackId, @PathVariable Long shelfId) {
        return repository.findByRackIdAndShelfId(rackId.toString(), shelfId.toString());
    }

    @PostMapping(value = "/addBook", consumes = {"application/json"})
    BookEntity newBook(@RequestBody BookEntity newBook) {
        return repository.save(newBook);
    }

    @GetMapping("/books/{id}")
    BookEntity one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    @GetMapping("/books/name/{name}")
    BookEntity oneByName(@PathVariable String name) {
        return repository.findByName(name).orElseThrow(() -> new BookNotFoundException(name));
    }

    @PutMapping("/books/{id}")
    BookEntity replaceBook(@RequestBody BookEntity newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.getShelf().addBook(book);
                    book.setShelf(newBook.getShelf());
                    return repository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
