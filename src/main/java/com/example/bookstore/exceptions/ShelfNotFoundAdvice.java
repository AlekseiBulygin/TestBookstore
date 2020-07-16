package com.example.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ShelfNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ShelfNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String shelfNotFoundHandler(ShelfNotFoundException ex) {
        return ex.getMessage();
    }
}
