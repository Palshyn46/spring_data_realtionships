package com.example.spring_data_relationships.exceptions;

public class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(Long id) {
        super("Entity is not found, id=" + id);
    }
}
