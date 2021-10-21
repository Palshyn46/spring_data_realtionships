package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.DepartmentDto;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public Optional<DepartmentDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        return null;
    }

    @Override
    public Optional<DepartmentDto> update(DepartmentDto departmentDto, Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
