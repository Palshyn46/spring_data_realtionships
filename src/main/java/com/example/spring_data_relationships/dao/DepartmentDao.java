package com.example.spring_data_relationships.dao;

import com.example.spring_data_relationships.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentDao extends CrudRepository<DepartmentEntity, Long> {
}
