package com.example.bookstore.exceptions;

public class ShelfNotFoundException extends RuntimeException {
    public ShelfNotFoundException (String id) {
        super("Couldn't find shelf with id: " + id);
    }
}
