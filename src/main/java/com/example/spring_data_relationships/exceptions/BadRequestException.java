package com.example.spring_data_relationships.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(Long id) {
        super("Something went wrong. Id=" + id);
    }
}
