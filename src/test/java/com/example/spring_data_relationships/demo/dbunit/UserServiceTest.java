package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.dto.UserDtoWithDepartment;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(SpringConfig.class)
@TestPropertySource(locations = {
        //"classpath:application.properties",
        "classpath:application-test.properties"})
@DatabaseTearDown
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUserDtoWhenCreate() {
        String expectedName = "name8";
        String expectedEmail = "email8";
        UserDto testUser = UserDto.builder().name(expectedName).email(expectedEmail).build();

        UserDto createdUser = userService.create(testUser);
        UserDto actualUser = userService.get(createdUser.getId()).orElseThrow(RuntimeException::new);

        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
        assertEquals(createdUser.getId(), actualUser.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUserDtoWhenGet() {
        Long expectedId = 1L;
        String expectedName = "name1";
        String expectedEmail = "email1";

        UserDto actualUser = userService.get(expectedId).orElseThrow(RuntimeException::new);

        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
        assertEquals(expectedId, actualUser.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUpdatedUserDtoWhenUpdate() {
        String expectedName = "updatedName";
        String expectedEmail = "updatedEmail";
        Long expectedId = 1L;
        UserDto testUser = UserDto.builder()
                .id(expectedId)
                .name(expectedName)
                .email(expectedEmail)
                .build();

        UserDto resultUser = userService.update(testUser, expectedId).get();

        assertEquals(expectedName, resultUser.getName());
        assertEquals(expectedEmail, resultUser.getEmail());
        assertEquals(expectedId, resultUser.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldVerifyRemoveMethodCallWhenDelete() {
        Long expectedId = 1L;

        UserService spyUserService = Mockito.spy(userService);
        spyUserService.delete(expectedId);
        Boolean exist = spyUserService.existsById(expectedId);

        //убрать verify
        //verify(spyUserService, times(1)).delete(expectedId);
        assertEquals(false, exist);
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldDeleteDepartmentFromUser() {
        Long expectedUserId = 1L;
        Long expectedDepartmentId = 1L;

        userService.deleteDepartmentFromUser(expectedUserId, expectedDepartmentId);
        UserDtoWithDepartment resultUser = userService.findUserDtoWithDepartment(expectedUserId);

        Assertions.assertNull(resultUser.getDepartment());
    }
}