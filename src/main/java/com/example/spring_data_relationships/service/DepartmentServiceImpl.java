package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.DepartmentDao;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.mappers.DepartmentMapper;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDao departmentDao;
    DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentDao departmentDao, DepartmentMapper departmentMapper) {
        this.departmentDao = departmentDao;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public Optional<DepartmentDto> get(Long id) {
        Optional<DepartmentEntity> department = departmentDao.findById(id);
        return department.map(departmentMapper::toDto);
    }

    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = departmentMapper.toEntity(departmentDto);
        departmentEntity = departmentDao.save(departmentEntity);
        return departmentMapper.toDto(departmentEntity);
    }

    @Override
    public Optional<DepartmentDto> update(DepartmentDto departmentDto, Long id) {
        DepartmentEntity departmentEntity = null;
        if (departmentDao.existsById(id)) {
            departmentDto.setId(id);
            departmentEntity = departmentDao.save(departmentMapper.toEntity(departmentDto));
        }
        return Optional.ofNullable(departmentMapper.toDto(departmentEntity));
    }

    @Override
    public void delete(Long id) {
        if (departmentDao.existsById(id)) {
            departmentDao.deleteById(id);
        }
    }
}
