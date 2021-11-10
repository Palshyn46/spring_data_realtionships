package com.example.spring_data_relationships.rabbitmq;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.UserIdDepartmentId;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.pro.packaged.S;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@EnableRabbit
@Component
public class RabbitMqListener {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @SneakyThrows
    @RabbitListener(queues = "addUser")
    public void processAddUserQueue(String message) {
        UserDto user = objectMapper.readValue(message, UserDto.class);
        UserDto createdUser = userService.create(user);
        System.out.println("Created user: " + createdUser);
    }

    @SneakyThrows
    @RabbitListener(queues = "addDepartment")
    public void processAddDepartmentQueue(String message) {
        DepartmentDto department = objectMapper.readValue(message, DepartmentDto.class);
        DepartmentDto createdDepartment = departmentService.create(department);
        System.out.println("Created department: " + createdDepartment);
    }

    @SneakyThrows
    @RabbitListener(queues = "addUserToDepartment")
    public void processAddUserToDepartment(@RequestBody String message) {
        UserIdDepartmentId userIdDepartmentId = objectMapper.readValue(message, UserIdDepartmentId.class);
        long userId = userIdDepartmentId.getUserId();
        long departmentId = userIdDepartmentId.getDepartmentId();
        departmentService.addUserToDepartment(userId, departmentId);
    }
}
