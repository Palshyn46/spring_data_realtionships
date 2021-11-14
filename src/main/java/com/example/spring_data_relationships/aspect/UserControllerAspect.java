package com.example.spring_data_relationships.aspect;

import com.example.spring_data_relationships.dto.CreateUserInformationDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.CreateUserInformationService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
public class UserControllerAspect {

    @Autowired
    CreateUserInformationService createUserInformationService;
    CreateUserInformationDto createUserInformationDto;

    @Before(value = "@annotation(com.example.spring_data_relationships.annotation.Action) && args(user)", argNames = "user")
    public void beforeMethodCall(UserDto user) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        createUserInformationDto = new CreateUserInformationDto(
                dtf.format(now),
                httpServletRequest.getRemoteAddr(),
                user.toString());
    }

    @AfterReturning(value = "@annotation(com.example.spring_data_relationships.annotation.Action)", returning = "result")
    public void afterReturningCallAt(Object result) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        createUserInformationDto.setExitTime(dtf.format(now));
        createUserInformationDto.setResponse(result.toString());
        createUserInformationService.save(createUserInformationDto);
    }
}
