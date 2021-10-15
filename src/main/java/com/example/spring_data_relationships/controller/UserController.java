package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto update(@RequestBody UserDto user, @PathVariable Long id) {
        return userService.update(user, id);
    }
}
