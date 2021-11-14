package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dao.CreateUserInformationDao;
import com.example.spring_data_relationships.dto.CreateUserInformationDto;
import com.example.spring_data_relationships.entity.CreateUserInformationEntity;
import com.example.spring_data_relationships.mappers.CreateUserInformationMapper;

public class CreateUserInformationServiceImpl implements CreateUserInformationService {

    CreateUserInformationDao createUserInformationDao;
    CreateUserInformationMapper createUserInformationMapper;

    public CreateUserInformationServiceImpl(CreateUserInformationDao createUserInformationDao,
                                            CreateUserInformationMapper createUserInformationMapper) {
        this.createUserInformationDao = createUserInformationDao;
        this.createUserInformationMapper = createUserInformationMapper;
    }

    @Override
    public CreateUserInformationDto save(CreateUserInformationDto createUserInformationDto) {
        CreateUserInformationEntity createUserInformationEntity =
                createUserInformationMapper.toEntity(createUserInformationDto);
        createUserInformationEntity = createUserInformationDao.save(createUserInformationEntity);
        return createUserInformationMapper.toDto(createUserInformationEntity);
    }
}
