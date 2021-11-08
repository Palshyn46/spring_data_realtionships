package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
@Slf4j
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping("/{id}")
    public DepartmentDto get(@PathVariable Long id) {
        return departmentService.get(id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto create(@RequestBody DepartmentDto department) {
        return departmentService.create(department);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto update(@RequestBody DepartmentDto department, @PathVariable Long id) {
        return departmentService.update(department, id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
}
