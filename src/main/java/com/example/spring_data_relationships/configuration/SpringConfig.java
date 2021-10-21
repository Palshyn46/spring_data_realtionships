package com.example.spring_data_relationships.configuration;

import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.DepartmentServiceImpl;
import com.example.spring_data_relationships.service.UserService;
import com.example.spring_data_relationships.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public DepartmentService departmentService() {
        return new DepartmentServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}