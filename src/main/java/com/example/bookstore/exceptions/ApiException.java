package com.example.bookstore.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException {

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
   private LocalDateTime timestamp;
   private HttpStatus status;
   private String message;
   private String debugMessage;
   private List<FieldValidationError> fieldValidationErrors;

   private ApiException() {
       timestamp = LocalDateTime.now();
   }

   ApiException(HttpStatus status) {
       this();
       this.status = status;
   }

   ApiException(HttpStatus status, Throwable ex) {
       this();
       this.status = status;
       this.message = "Unexpected error";
       this.debugMessage = ex.getLocalizedMessage();
   }

   ApiException(HttpStatus status, String message, Throwable ex) {
       this();
       this.status = status;
       this.message = message;
       this.debugMessage = ex.getLocalizedMessage();
   }

    private void addFieldError(FieldValidationError error) {
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(error);
    }

    public void addValidationFieldErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationFieldErr);
    }

    private void addValidationFieldErr(FieldError fieldError) {
        addFieldError(new FieldValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()));
    }

    public void addValidationGlobalError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationErrorObject);
    }

    private void addValidationErrorObject(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    private void addValidationError(String object, String message) {
        addFieldError(new FieldValidationError(object, message));
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        addFieldError(new FieldValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage()));
    }
}