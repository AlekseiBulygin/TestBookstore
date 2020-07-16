package com.example.bookstore.controller;

import com.example.bookstore.exceptions.*;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.repo.RackRepository;
import com.example.bookstore.repo.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookstoreController {
    private final BookRepository repository;

    @Autowired
    RackRepository rackRepository;

    @Autowired
    ShelfRepository shelfRepository;

    BookstoreController (BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    Iterable<BookEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/books/rack/{id}")
    Iterable<BookEntity> byRackId(@PathVariable Long id) {
        rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id.toString()));
        return repository.findByRackId(id.toString());
    }

    @GetMapping("/books/shelf/{id}")
    Iterable<BookEntity> byShelfId(@PathVariable Long id) {
        shelfRepository.findById(id).orElseThrow(() -> new ShelfNotFoundException(id.toString()));
        return repository.findByShelfId(id);
    }

    @GetMapping("/books/rack_shelf/{rackId}/{shelfId}")
    Iterable<BookEntity> byRackIdAndShelfId(@PathVariable Long rackId, @PathVariable Long shelfId) {
        rackRepository.findById(rackId).orElseThrow(() -> new RackNotFoundException(rackId.toString()));
        shelfRepository.findById(shelfId).orElseThrow(() -> new ShelfNotFoundException(shelfId.toString()));
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
        repository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        repository.deleteById(id);
    }
}
