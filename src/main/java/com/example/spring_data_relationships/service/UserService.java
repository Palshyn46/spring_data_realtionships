package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto create(UserDto user);

    Optional<UserDto> get(Long id);

    Optional<UserDto> update(UserDto user, Long id);

    void delete(Long id);

    boolean existsById(Long id);
}
