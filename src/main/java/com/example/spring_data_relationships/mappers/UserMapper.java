package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity user);

    Iterable<UserDto> usersToUserDtos(Iterable<UserEntity> users);

    UserEntity toEntity(UserDto user);

    Iterable<UserEntity> dtosToUserEntities(Iterable<UserDto> users);
}
