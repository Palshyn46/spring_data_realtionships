package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.DepartmentDtoWithUserDto;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringJUnitConfig(SpringConfig.class)
@TestPropertySource(locations = {
        "classpath:application-test.properties"})
@DatabaseTearDown
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnDepartmentDtoWhenCreate() {
        String expectedName = "name8";
        DepartmentDto testDepartment = DepartmentDto.builder().name(expectedName).build();

        DepartmentDto createdDepartment = departmentService.create(testDepartment);
        DepartmentDto actualDepartment = departmentService.get(createdDepartment.getId()).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualDepartment.getName());
        Assertions.assertEquals(createdDepartment.getId(), actualDepartment.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnDepartmentDtoWhenGet() {
        Long expectedId = 1L;
        String expectedName = "name1";

        DepartmentDto actualDepartment = departmentService.get(expectedId).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualDepartment.getName());
        Assertions.assertEquals(expectedId, actualDepartment.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUpdatedDepartmentDtoWhenUpdate() {
        String expectedName = "updatedName";
        Long expectedId = 1L;
        DepartmentDto testDepartment = DepartmentDto.builder()
                .id(expectedId)
                .name(expectedName)
                .build();

        DepartmentDto updatedDepartment = departmentService.update(testDepartment, expectedId)
                .orElseThrow(RuntimeException::new);
        DepartmentDto actualDepartment = departmentService.get(expectedId).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualDepartment.getName());
        Assertions.assertEquals(expectedId, actualDepartment.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldVerifyRemoveDepartmentWhenDelete() {
        Long expectedId = 1L;

        departmentService.delete(expectedId);
        Boolean exist = departmentService.existsById(expectedId);

        Assertions.assertEquals(false, exist);
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldAddUserToDepartment() {
        Long departmentId = 1L;
        Long userId = 1L;

        departmentService.addUserToDepartment(userId, departmentId);
        DepartmentDtoWithUserDto department = departmentService
                .findDepartmentDtoWithUserDtoById(departmentId);

        Assertions.assertEquals(1, department.getUsers().size());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldDeleteUserFromDepartment() {
        Long userId = 1L;
        Long departmentId = 1L;

        departmentService.addUserToDepartment(userId, departmentId);
        DepartmentDtoWithUserDto departmentBeforeDelete = departmentService
                .findDepartmentDtoWithUserDtoById(departmentId);

        departmentService.deleteUserFromDepartment(userId, departmentId);
        DepartmentDtoWithUserDto departmentAfterDelete = departmentService
                .findDepartmentDtoWithUserDtoById(departmentId);

        Assertions.assertEquals(1, departmentBeforeDelete.getUsers().size());
        Assertions.assertEquals(0, departmentAfterDelete.getUsers().size());
    }
}