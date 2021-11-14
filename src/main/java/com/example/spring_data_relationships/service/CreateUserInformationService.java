package com.example.spring_data_relationships.service;

import com.example.spring_data_relationships.dto.CreateUserInformationDto;

public interface CreateUserInformationService {

    CreateUserInformationDto save(CreateUserInformationDto createUserInformationDto);
}
