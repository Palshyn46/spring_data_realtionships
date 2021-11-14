package com.example.spring_data_relationships.dao;

import com.example.spring_data_relationships.entity.CreateUserInformationEntity;
import org.springframework.data.repository.CrudRepository;

public interface CreateUserInformationDao extends CrudRepository<CreateUserInformationEntity, Long> {
}
