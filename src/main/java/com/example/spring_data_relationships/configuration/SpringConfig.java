package com.example.spring_data_relationships.configuration;

import com.example.spring_data_relationships.dao.DepartmentDao;
import com.example.spring_data_relationships.dao.GroupDao;
import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.mappers.DepartmentMapper;
import com.example.spring_data_relationships.mappers.GroupMapper;
import com.example.spring_data_relationships.mappers.UserMapper;
import com.example.spring_data_relationships.service.*;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.spring_data_relationships")
public class SpringConfig {

    @Bean
    public DepartmentService departmentService(DepartmentDao departmentDao,
                                               DepartmentMapper departmentMapper,
                                               UserDao userDao) {
        return new DepartmentServiceImpl(departmentDao, departmentMapper, userDao);
    }

    @Bean
    public UserService userService(UserDao userDao,
                                   UserMapper userMapper) {
        return new UserServiceImpl(userDao, userMapper);
    }

    @Bean
    public GroupService groupService(GroupDao groupDao,
                                     GroupMapper groupMapper) {
        return new GroupServiceImpl(groupDao, groupMapper);
    }


}