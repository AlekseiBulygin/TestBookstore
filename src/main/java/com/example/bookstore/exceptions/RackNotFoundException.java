package com.example.bookstore.exceptions;

public class RackNotFoundException extends RuntimeException {
    public RackNotFoundException (String id) {
        super("Couldn't find rack with id: " + id);
    }

}
