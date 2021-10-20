package com.example.spring_data_relationships.controller;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
@Slf4j
public class DepartmentController {

    DepartmentService departmentService;

    @GetMapping("/{id}")
    public DepartmentDto get(@PathVariable Long id) {
        return departmentService.get(id).orElseThrow(() -> new MyEntityNotFoundException(id));
    }

}
