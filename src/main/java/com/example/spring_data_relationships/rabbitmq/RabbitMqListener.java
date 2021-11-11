package com.example.spring_data_relationships.rabbitmq;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.UserIdDepartmentId;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@EnableRabbit
@Component
public class RabbitMqListener {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @RabbitListener(queues = "addUser")
    public void processAddUserQueue(String message) {
        UserDto user = objectMapper.readValue(message, UserDto.class);
        UserDto createdUser = userService.create(user);
    }

    @SneakyThrows
    @RabbitListener(queues = "addDepartment")
    public void processAddDepartmentQueue(String message) {
        DepartmentDto department = objectMapper.readValue(message, DepartmentDto.class);
        DepartmentDto createdDepartment = departmentService.create(department);
    }

    @SneakyThrows
    @RabbitListener(queues = "addUserToDepartment")
    public void processAddUserToDepartment(@RequestBody String message) {
        UserIdDepartmentId userIdDepartmentId = objectMapper.readValue(message, UserIdDepartmentId.class);
        long userId = userIdDepartmentId.getIdValueU();
        long departmentId = userIdDepartmentId.getIdValueD();
        departmentService.addUserToDepartment(userId, departmentId);
    }
}
