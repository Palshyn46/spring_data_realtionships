package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto toDto(DepartmentEntity department);

    Iterable<DepartmentDto> departmentsToDepartmentDtos(Iterable<DepartmentEntity> departments);

    DepartmentEntity toEntity(DepartmentDto department);

    Iterable<DepartmentEntity> dtosToDepartmentEntities(Iterable<DepartmentDto> departments);
}
