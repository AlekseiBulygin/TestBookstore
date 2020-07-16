package com.example.bookstore.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException (String id) {
        super("Couldn't find book with id: " + id);
    }
}
