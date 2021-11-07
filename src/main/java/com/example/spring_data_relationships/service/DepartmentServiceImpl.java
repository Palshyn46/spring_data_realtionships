package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.DepartmentDao;
import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import com.example.spring_data_relationships.mappers.DepartmentMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDao departmentDao;
    DepartmentMapper departmentMapper;
    UserDao userDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao,
                                 DepartmentMapper departmentMapper,
                                 UserDao userDao) {
        this.departmentDao = departmentDao;
        this.departmentMapper = departmentMapper;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public Optional<DepartmentDto> get(Long id) {
        Optional<DepartmentEntity> department = departmentDao.findById(id);
        return department.map(departmentMapper::toDto);
    }

    @Transactional
    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = departmentMapper.toEntity(departmentDto);
        departmentEntity = departmentDao.save(departmentEntity);
        return departmentMapper.toDto(departmentEntity);
    }

    @Transactional
    @Override
    public Optional<DepartmentDto> update(DepartmentDto departmentDto, Long id) {
        DepartmentEntity departmentEntity = null;
        if (departmentDao.existsById(id)) {
            departmentDto.setId(id);
            departmentEntity = departmentDao.save(departmentMapper.toEntity(departmentDto));
        }
        return Optional.ofNullable(departmentMapper.toDto(departmentEntity));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (departmentDao.existsById(id)) {
            departmentDao.deleteById(id);
        }
    }

    @Transactional
    @Override
    public boolean existsById(Long id) {
        return departmentDao.existsById(id);
    }

    @Transactional
    @Override
    public DepartmentDtoWithUserDto findDepartmentDtoWithUserDtoById(Long departmentId) {
        Optional<DepartmentEntity> department = departmentDao.findById(departmentId);
        return department.map(departmentMapper::toDepartmentDtoWithUserDto).orElse(null);
    }

    @Override
    public DepartmentDtoWithUserDto saveDepartmentDtoWithUserDto(DepartmentDtoWithUserDto departmentDtoWithUserDto) {
        DepartmentEntity departmentEntity = departmentMapper.toDepartmentEntity(departmentDtoWithUserDto);
        departmentEntity = departmentDao.save(departmentEntity);
        return departmentMapper.toDepartmentDtoWithUserDto(departmentEntity);
    }

    @Transactional
    @Override
    public void addUserToDepartment(Long userId, Long departmentId) {
        userDao
                .findById(userId)
                .map(usr -> {
                    departmentDao
                            .findById(departmentId)
                            .map(dep -> {
                                List<UserEntity> users = dep.getUsers();
                                users.add(usr);
                                dep.setUsers(users);
                                usr.setDepartment(dep);
                                return null;
                            });
                    return null;
                });
    }

    @Transactional
    @Override
    public void deleteUserFromDepartment(Long userId, Long departmentId) {
        userDao
                .findById(userId)
                .map(usr -> {
                    departmentDao
                            .findById(departmentId)
                            .map(dep -> {
                                List<UserEntity> users = dep.getUsers();
                                users.remove(usr);
                                dep.setUsers(users);
                                return null;
                            });
                    return null;
                });
    }
}
