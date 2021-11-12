package com.example.spring_data_relationships.aspect;


import com.example.spring_data_relationships.dto.UserDto;
import liquibase.pro.packaged.A;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
public class UserControllerAspect {

    @Pointcut(value = "@annotation(com.example.spring_data_relationships.annotation.Action) && args(user, request)", argNames = "user,request")
    public void controllerMethodCall(UserDto user, HttpServletRequest request) {
    }

    @Before(value = "controllerMethodCall(user, request)", argNames = "user,request")
    public void beforeMethodCall(UserDto user, HttpServletRequest request) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("Enter time: " + dtf.format(now));
        log.info("User: " + user.toString());
        log.info("ip address: " + request.getRemoteAddr());
    }

    @AfterReturning(value = "@annotation(com.example.spring_data_relationships.annotation.Action)", returning = "result")
    public void afterReturningCallAt(Object result) {
            log.info("Response: " + result.toString());
    }
}
