package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.*;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.entity.GroupEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity user);

    Iterable<UserDto> usersToUserDtos(Iterable<UserEntity> users);

    UserEntity toEntity(UserDto user);

    Iterable<UserEntity> dtosToUserEntities(Iterable<UserDto> users);


    UserDtoWithDepartment toUserDtoWithDepartment(UserEntity userEntity);

    Iterable<UserDtoWithDepartment> userEntitiesToUserDtosWithDepartment(Iterable<UserEntity> users);

    UserEntity toUserEntity(UserDtoWithDepartment user);

    Iterable<UserEntity> userDtosWithDepartmentToUserEntities(Iterable<UserDtoWithDepartment> users);


    DepartmentDto toDto(DepartmentEntity department);

    Iterable<DepartmentDto> departmentsToDepartmentDtos(Iterable<DepartmentEntity> departments);

    DepartmentEntity toEntity(DepartmentDto department);

    Iterable<DepartmentEntity> dtosToDepartmentEntities(Iterable<DepartmentDto> departments);


    UserDtoWithGroups toUserDtoWithGroups(UserEntity userEntity);

    Iterable<UserDtoWithGroups> userEntitiesToUserDtosWithGroups(Iterable<UserEntity> users);

    UserEntity toUserEntity(UserDtoWithGroups user);

    Iterable<UserEntity> userDtosWithGroupsToUserEntities(Iterable<UserDtoWithGroups> users);


    GroupDto toDto(GroupEntity group);

    Iterable<GroupDto> groupsToGroupDtos(Iterable<GroupEntity> groups);

    GroupEntity toEntity(GroupDto group);

    Iterable<GroupEntity> dtosToGroupEntities(Iterable<GroupDto> groups);
}
