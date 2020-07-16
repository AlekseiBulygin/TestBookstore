package com.example.bookstore.exceptions;

import java.util.Optional;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException (String id) {
        super("Couldn't find book " + id);
    }
}
