package com.example.spring_data_relationships.configuration;

import com.example.spring_data_relationships.dao.DepartmentDao;
import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.mappers.DepartmentMapper;
import com.example.spring_data_relationships.mappers.UserMapper;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.DepartmentServiceImpl;
import com.example.spring_data_relationships.service.UserService;
import com.example.spring_data_relationships.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.spring_data_relationships")
public class SpringConfig {

    @Bean
    public DepartmentService departmentService(DepartmentDao departmentDao, DepartmentMapper departmentMapper) {
        return new DepartmentServiceImpl(departmentDao, departmentMapper);
    }

    @Bean
    public UserService userService(UserDao userDao, UserMapper userMapper, DepartmentService departmentService) {
        return new UserServiceImpl(userDao, userMapper, departmentService);
    }
}