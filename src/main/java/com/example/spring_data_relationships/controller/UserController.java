package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        log.info(String.valueOf(id));
        return userService.get(id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto update(@RequestBody UserDto user, @PathVariable Long id) {
        return userService.update(user, id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
