package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto toDto(DepartmentEntity department);

    Iterable<DepartmentDto> departmentsToDepartmentDtos(Iterable<DepartmentEntity> departments);

    DepartmentEntity toEntity(DepartmentDto department);

    Iterable<DepartmentEntity> dtosToDepartmentEntities(Iterable<DepartmentDto> departments);


    DepartmentDtoWithUserDto toDepartmentDtoWithUserDto(DepartmentEntity department);

    Iterable<DepartmentDtoWithUserDto> departmentEntitiesToDepartmentDtosWithUserDto(
            Iterable<DepartmentEntity> departments);

    DepartmentEntity toDepartmentEntity(DepartmentDtoWithUserDto department);

    Iterable<DepartmentEntity> departmentDtosWithUserDtoToDepartmentEntities(Iterable<DepartmentDto> departments);


    UserDto toUserDto(UserEntity user);

    Iterable<UserDto> usersToUserDtos(Iterable<UserEntity> users);

    UserEntity toUserEntity(UserDto user);

    Iterable<UserEntity> dtosToUserEntities(Iterable<UserDto> users);
}
