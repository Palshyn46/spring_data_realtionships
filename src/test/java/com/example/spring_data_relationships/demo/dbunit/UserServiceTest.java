package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.UserService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

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

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUserDtoWhenCreate() {
        String expectedName = "name8";
        String expectedEmail = "email8";
        UserDto testUser = UserDto.builder().name(expectedName).email(expectedEmail).build();

        UserDto createdUser = userService.create(testUser);
        UserDto actualUser = userService.get(createdUser.getId()).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualUser.getName());
        Assertions.assertEquals(expectedEmail, actualUser.getEmail());
        Assertions.assertEquals(createdUser.getId(), actualUser.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUserDtoWhenGet() {
        Long expectedId = 1L;
        String expectedName = "name1";
        String expectedEmail = "email1";

        UserDto actualUser = userService.get(expectedId).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualUser.getName());
        Assertions.assertEquals(expectedEmail, actualUser.getEmail());
        Assertions.assertEquals(expectedId, actualUser.getId());
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

        Assertions.assertEquals(expectedName, resultUser.getName());
        Assertions.assertEquals(expectedEmail, resultUser.getEmail());
        Assertions.assertEquals(expectedId, resultUser.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldVerifyRemoveMethodCallWhenDelete() {
        Long expectedId = 1L;

        UserService spyUserService = Mockito.spy(userService);
        spyUserService.delete(expectedId);
        Boolean exist = spyUserService.existsById(expectedId);

        verify(spyUserService, times(1)).delete(expectedId);
        Assertions.assertEquals(false, exist);
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnListOfUserDtos() {
        String expectedName = "name8";
        String expectedEmail = "email8";
        Long departmentId = 1L;
        UserDto testUserDto = UserDto.builder().name(expectedName).email(expectedEmail).departmentId(departmentId).build();

        userService.addUserToDepartment(testUserDto, departmentId);
        Optional<UserDto> userDtoOptional = userService.get(8L);
        UserDto resultUserDto = userService.get(8L).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, resultUserDto.getName());
        Assertions.assertEquals(expectedEmail, resultUserDto.getEmail());
    }
}