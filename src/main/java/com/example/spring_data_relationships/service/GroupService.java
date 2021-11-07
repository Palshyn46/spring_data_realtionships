package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.dto.GroupDtoWithUsers;

import java.util.Optional;

public interface GroupService {

    GroupDto create(GroupDto group);

    Optional<GroupDto> get(Long id);

    Optional<GroupDto> update(GroupDto group, Long id);

    void delete(Long id);

    boolean existsById(Long id);

    GroupDtoWithUsers findGroupDtoWithUsers(Long id);

    void deleteUserFromGroup(Long expectedGroupId, Long expectedUserId);
}
