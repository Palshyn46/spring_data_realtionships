package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.DepartmentDto;

import java.util.Optional;

public interface DepartmentService {

    Optional<DepartmentDto> get(Long id);
}
