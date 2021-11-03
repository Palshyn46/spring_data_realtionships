package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;

import java.util.Optional;

public interface DepartmentService {

    Optional<DepartmentDto> get(Long id);

    DepartmentDto create(DepartmentDto departmentDto);

    Optional<DepartmentDto> update(DepartmentDto departmentDto, Long id);

    void delete(Long id);

    boolean existsById(Long id);

    DepartmentDtoWithUserDto findDepartmentDtoWithUserDtoById(Long departmentId);

    DepartmentDtoWithUserDto saveDepartmentDtoWithUserDto(DepartmentDtoWithUserDto departmentDtoWithUserDto);
}
