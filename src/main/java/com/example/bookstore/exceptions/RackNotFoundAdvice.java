package com.example.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice

public class RackNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String rackNotFoundHandler(RackNotFoundException ex) {
        return ex.getMessage();
    }

}
