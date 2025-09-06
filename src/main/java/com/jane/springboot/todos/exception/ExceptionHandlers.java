package com.jane.springboot.todos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponses> handleException(ResponseStatusException exc){
        return buildErrorException(exc, HttpStatus.valueOf(exc.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponses> handleException(Exception exc){
        return buildErrorException(exc, HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<ErrorResponses> buildErrorException(Exception exc, HttpStatus status){
        ErrorResponses error = new ErrorResponses();
        error.setStatus(status.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorResponses>(error, status);
    }
}
