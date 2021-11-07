package com.example.spring_data_relationships.dao;

import com.example.spring_data_relationships.entity.UsersGroupsEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersGroupsDao extends CrudRepository<UsersGroupsEntity, Long> {

}
