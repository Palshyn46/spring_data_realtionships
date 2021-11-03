package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.DepartmentDao;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.mappers.DepartmentMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDao departmentDao;
    DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentDao departmentDao, DepartmentMapper departmentMapper) {
        this.departmentDao = departmentDao;
        this.departmentMapper = departmentMapper;
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
}
