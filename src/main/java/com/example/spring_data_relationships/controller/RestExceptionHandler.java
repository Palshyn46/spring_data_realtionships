package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.exceptions.ApiError;
import com.example.spring_data_relationships.exceptions.BadRequestException;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler({MyEntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(MyEntityNotFoundException e) {
        ApiError apiError = new ApiError("Entity not found exception", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        log.info("in method");
        ApiError apiError = new ApiError("Something went wrong", e.getMessage());
        return new ResponseEntity<>("apiError", HttpStatus.BAD_REQUEST);
    }
}
