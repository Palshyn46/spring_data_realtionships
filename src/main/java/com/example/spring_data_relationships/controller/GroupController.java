package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@AllArgsConstructor
@Slf4j
public class GroupController {

    private GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto create(@RequestBody GroupDto group) {
        return groupService.create(group);
    }

    @GetMapping("/{id}")
    public GroupDto get(@PathVariable Long id) {
        log.info(String.valueOf(id));
        return groupService.get(id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto update(@RequestBody GroupDto group, @PathVariable Long id) {
        return groupService.update(group, id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

}
