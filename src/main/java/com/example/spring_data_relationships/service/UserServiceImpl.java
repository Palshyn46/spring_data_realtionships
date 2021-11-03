package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.UserEntity;
import com.example.spring_data_relationships.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    UserDao userDao;
    UserMapper userMapper;
    DepartmentService departmentService;

    public UserServiceImpl(UserDao userDao, UserMapper userMapper, DepartmentService departmentService) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.departmentService = departmentService;
    }

    @Transactional
    @Override
    public UserDto create(UserDto user) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntity = userDao.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    @Transactional
    @Override
    public Optional<UserDto> get(Long id) {
        Optional<UserEntity> user = userDao.findById(id);
        return user.map(userMapper::toDto);
    }

    @Transactional
    @Override
    public Optional<UserDto> update(UserDto user, Long id) {
        UserEntity userEntity = null;
        if (userDao.existsById(id)) {
            user.setId(id);
            userEntity = userDao.save(userMapper.toEntity(user));
        }
        return Optional.ofNullable(userMapper.toDto(userEntity));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
        }
    }

    @Transactional
    @Override
    public boolean existsById(Long id) {
        return userDao.existsById(id);
    }

    @Transactional
    @Override
    public void addUserToDepartment(UserDto user, Long departmentId) {
        DepartmentDtoWithUserDto department = departmentService.findDepartmentDtoWithUserDtoById(departmentId);
        List<UserDto> users = department.getUsers();
        users.add(user);
        department.setUsers(users);
        departmentService.saveDepartmentDtoWithUserDto(department);
    }
}
