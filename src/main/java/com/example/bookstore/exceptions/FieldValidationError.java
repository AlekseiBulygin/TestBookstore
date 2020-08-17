package com.example.bookstore.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FieldValidationError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    FieldValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{object='" + object + '\'' +
                ", field='" + field + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", message='" + message + '\'' +
                '}';
    }
}
