package com.example.spring_data_relationships.mappers;

import com.example.spring_data_relationships.dto.GroupDto;
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
}
