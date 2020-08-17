package com.example.bookstore.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        LOGGER.error("BookNotFoundException: {}", ex.getMessage());
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND);
        apiException.setMessage(ex.getMessage());
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST);
        apiException.setMessage("Validation error");
        apiException.addValidationErrors(ex.getConstraintViolations());
        LOGGER.error("Validation error: {}",apiException.getFieldValidationErrors().toString());
        return buildResponseEntity(apiException);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("HttpMessageNotReadableException: {}", ex.getLocalizedMessage().split(";")[0]);
        return buildResponseEntity(new ApiException(status, "Malformed JSON request", ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST);
        apiException.setMessage("Validation error");
        apiException.addValidationFieldErrors(ex.getBindingResult().getFieldErrors());
        apiException.addValidationGlobalError(ex.getBindingResult().getGlobalErrors());
        LOGGER.error("Validation error: {}",apiException.getFieldValidationErrors().toString());
        return buildResponseEntity(apiException);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
       return new ResponseEntity<Object>(apiException, apiException.getStatus());
    }
}
