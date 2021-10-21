package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.UserDto;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public UserDto create(UserDto user) {
        return null;
    }

    @Override
    public Optional<UserDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> update(UserDto user, Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
