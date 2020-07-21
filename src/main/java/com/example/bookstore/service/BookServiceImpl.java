package com.example.bookstore.service;

import com.example.bookstore.exceptions.*;
import com.example.bookstore.objects.BookEntity;
import com.example.bookstore.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    RackRepository rackRepository;

    @Autowired
    ShelfRepository shelfRepository;

    public BookServiceImpl(BookRepository repo) {
        this.bookRepository = repo;
    }

    @Override
    public BookEntity save(BookEntity newBook) {
        //do some logic
        return this.bookRepository.save(newBook);
    }

    @Override
    public List<BookEntity> findAll() {
        Iterator<BookEntity> iterator = bookRepository.findAll().iterator();
        ArrayList<BookEntity> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);
        return books;
    }
    @Override
    public BookEntity findByName(String name) {
        return bookRepository.findByName(name).orElseThrow(() -> new BookNotFoundException(name));
    }

    @Override
    public BookEntity findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    @Override
    public List<BookEntity> findByRackId(Long id) {
        rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id.toString()));
        String rackId = id.toString();
        return bookRepository.findByRackId(rackId);
    }

    @Override
    public List<BookEntity> findByShelfId(Long id) {
        shelfRepository.findById(id).orElseThrow(() -> new ShelfNotFoundException(id.toString()));
        return bookRepository.findByShelfId(id);
    }

    @Override
    public List<BookEntity> findByRackIdAndShelfId(Long rackId, Long shelfId) {
        rackRepository.findById(rackId).orElseThrow(() -> new RackNotFoundException(rackId.toString()));
        shelfRepository.findById(shelfId).orElseThrow(() -> new ShelfNotFoundException(shelfId.toString()));
        return bookRepository.findByRackIdAndShelfId(rackId.toString(), shelfId.toString());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        bookRepository.deleteById(id);
    }

    @Override
    public BookEntity replaceBook(BookEntity newBook, Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.getShelf().addBook(book);
                    book.setShelf(newBook.getShelf());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id.toString()));
    }
}
