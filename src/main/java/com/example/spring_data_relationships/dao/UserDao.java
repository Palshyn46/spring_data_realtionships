package com.example.spring_data_relationships.dao;

import com.example.spring_data_relationships.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity, Long> {
}
