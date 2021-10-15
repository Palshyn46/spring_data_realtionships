package com.example.spring_data_relationships.dao;

import com.example.spring_data_relationships.entity.GroupEntity;
import org.springframework.data.repository.CrudRepository;

public interface GroupDao extends CrudRepository<GroupEntity, Long> {
}
