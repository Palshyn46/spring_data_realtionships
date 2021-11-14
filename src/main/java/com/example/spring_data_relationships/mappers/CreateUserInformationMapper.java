package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.CreateUserInformationDto;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.entity.CreateUserInformationEntity;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateUserInformationMapper {

    CreateUserInformationDto toDto(CreateUserInformationEntity createUserInformation);

    Iterable<CreateUserInformationDto> createUserInformationEntitiesToCreateUserInformationDtos
            (Iterable<CreateUserInformationEntity> createUserInformationEntities);

    CreateUserInformationEntity toEntity(CreateUserInformationDto createUserInformation);

    Iterable<CreateUserInformationEntity> createUserInformationDtosToCreateUserInformationEntities
            (Iterable<CreateUserInformationDto> createUserInformationDtos);
}
