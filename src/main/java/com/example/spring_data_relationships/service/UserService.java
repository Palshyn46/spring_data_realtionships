package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.dto.UserDtoWithDepartment;
import com.example.spring_data_relationships.dto.UserDtoWithGroups;

import java.util.Optional;

public interface UserService {

    UserDto create(UserDto user);

    Optional<UserDto> get(Long id);

    Optional<UserDto> update(UserDto user, Long id);

    void delete(Long id);

    boolean existsById(Long id);

    UserDtoWithDepartment findUserDtoWithDepartment(Long id);

    UserDtoWithGroups findUserDtoWithGroups(Long id);

    void deleteDepartmentFromUser(Long expectedUserId, Long expectedDepartmentId);

    void addGroupToUser(Long groupId, Long userId);

    void deleteGroupFromUser(Long groupId, Long userId);
}
