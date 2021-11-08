package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.dto.GroupDtoWithUsers;
import com.example.spring_data_relationships.service.GroupService;
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
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnGroupDtoWhenCreate() {
        String expectedName = "name8";
        GroupDto testGroup = GroupDto.builder().name(expectedName).build();

        GroupDto createdGroup = groupService.create(testGroup);
        GroupDto actualGroup = groupService.get(createdGroup.getId()).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualGroup.getName());
        Assertions.assertEquals(createdGroup.getId(), actualGroup.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnGroupDtoWhenGet() {
        Long expectedId = 1L;
        String expectedName = "name1";

        GroupDto actualGroup = groupService.get(expectedId).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualGroup.getName());
        Assertions.assertEquals(expectedId, actualGroup.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnGroupDtoWithUsersWhenGet() {
        Long expectedId = 1L;
        String expectedName = "name1";

        GroupDtoWithUsers actualGroup = groupService.findGroupDtoWithUsers(expectedId);

        Assertions.assertEquals(expectedName, actualGroup.getName());
        Assertions.assertEquals(expectedId, actualGroup.getId());
        Assertions.assertEquals(0, actualGroup.getUsers().size());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldReturnUpdatedGroupDtoWhenUpdate() {
        String expectedName = "updatedName";
        Long expectedId = 1L;
        GroupDto testGroup = GroupDto.builder()
                .id(expectedId)
                .name(expectedName)
                .build();

        groupService.update(testGroup, expectedId).orElseThrow(RuntimeException::new);
        GroupDto actualGroup = groupService.get(expectedId).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(expectedName, actualGroup.getName());
        Assertions.assertEquals(expectedId, actualGroup.getId());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldVerifyRemoveGroupWhenDelete() {
        Long expectedId = 1L;

        groupService.delete(expectedId);
        Boolean exist = groupService.existsById(expectedId);

        Assertions.assertEquals(false, exist);
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldAddUserToGroup() {
        Long groupId = 1L;
        Long userId = 1L;

        groupService.addUserToGroup(userId, groupId);
        GroupDtoWithUsers group = groupService
                .findGroupDtoWithUsers(groupId);

        Assertions.assertEquals(1, group.getUsers().size());
    }

    @SneakyThrows
    @DatabaseSetup("classpath:dbunit/data.xml")
    @Test
    public void shouldDeleteUserFromGroup() {
        Long userId = 1L;
        Long groupId = 1L;

        groupService.addUserToGroup(userId, groupId);
        GroupDtoWithUsers groupBeforeDelete = groupService
                .findGroupDtoWithUsers(groupId);

        groupService.deleteUserFromGroup(userId, groupId);
        GroupDtoWithUsers groupAfterDelete = groupService
                .findGroupDtoWithUsers(groupId);

        Assertions.assertEquals(1, groupBeforeDelete.getUsers().size());
        Assertions.assertEquals(0, groupAfterDelete.getUsers().size());
    }
}