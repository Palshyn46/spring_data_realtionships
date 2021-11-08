package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.dto.GroupDtoWithUsers;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.GroupEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupDto toDto(GroupEntity group);

    Iterable<GroupDto> groupsToGroupDtos(Iterable<GroupEntity> groups);

    GroupEntity toEntity(GroupDto group);

    Iterable<GroupEntity> dtosToGroupEntities(Iterable<GroupDto> groups);


    GroupDtoWithUsers toGroupDtoWithUsers(GroupEntity group);

    Iterable<GroupDtoWithUsers> groupsToGroupDtosWithUsers(Iterable<GroupEntity> groups);

    GroupEntity toEntity(GroupDtoWithUsers group);

    Iterable<GroupEntity> GroupDtosWithUsersToGroupEntities(Iterable<GroupDtoWithUsers> groups);


    UserDto toDto(UserEntity user);

    Iterable<UserDto> usersToUserDtos(Iterable<UserEntity> users);

    UserEntity toEntity(UserDto user);

    Iterable<UserEntity> dtosToUserEntities(Iterable<UserDto> users);
}
