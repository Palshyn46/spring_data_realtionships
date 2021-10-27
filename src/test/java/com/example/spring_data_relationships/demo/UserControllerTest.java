package com.example.spring_data_relationships.demo;

import com.example.spring_data_relationships.controller.DepartmentController;
import com.example.spring_data_relationships.controller.UserController;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfUserAbsent() {
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn200IfUserExist() {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();

        when(userService.get(eq(expectedId))).thenReturn(Optional.of(testUser));

        MvcResult result = this.mockMvc.perform(get("/user/{id}", expectedId)).andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserDto expectedUser = objectMapper.readValue(content, UserDto.class);
        assertEquals(expectedId, expectedUser.getId());
        assertEquals(expectedName, expectedUser.getName());
        assertEquals(expectedEmail, expectedUser.getEmail());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndUserWithIdWhenCreateUser() {
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        Long expectedId = 1L;
        UserDto testUser = UserDto.builder().name(expectedName).email(expectedEmail).build();

        when(userService.create(any(UserDto.class)))
                .thenReturn(
                        UserDto.builder()
                                .id(expectedId)
                                .name(expectedName)
                                .email(expectedEmail).build()
                );

        MvcResult result = this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserDto actualUser = objectMapper.readValue(content, UserDto.class);
        assertEquals(expectedId, actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndUpdatedUserWhenUpdate() {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();

        when(userService.update(any(UserDto.class), eq(expectedId)))
                .thenReturn(Optional.of(
                        UserDto.builder()
                                .id(expectedId)
                                .name(expectedName)
                                .email(expectedEmail).build())
                );

        MvcResult result = this.mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserDto actualUser = objectMapper.readValue(content, UserDto.class);
        assertEquals(actualUser.getId(), 1);
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfUserNotExistWhenUpdate() {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();
        this.mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfUserNotExistRemove() {
        Long expectedId = 1L;
        doThrow(MyEntityNotFoundException.class).when(userService).delete(expectedId);
        this.mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn204IfUserExistAndRemove() {
        Long expectedId = 1L;
        this.mockMvc.perform(delete("/user/{id}", expectedId))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).delete(expectedId);
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringGet() {
        Long expectedId = 1L;
        String errorMessage = "Something went wrong";

        doThrow(RuntimeException.class).when(userService).get(expectedId);

        MvcResult result = this.mockMvc.perform(get("/user/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringPost() {
        String errorMessage = "Something went wrong";
        UserDto testUser = UserDto.builder().name("name").email("email@email.com").build();

        doThrow(RuntimeException.class).when(userService).create(any(UserDto.class));

        MvcResult result = this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringPut() {
        String errorMessage = "Something went wrong";
        Long expectedId = 1L;
        UserDto testUser = UserDto.builder().name("name").email("email@email.com").build();

        doThrow(RuntimeException.class).when(userService).update(any(UserDto.class), eq(expectedId));

        MvcResult result = this.mockMvc.perform(put("/user/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringDelete() {
        String errorMessage = "Something went wrong";
        Long expectedId = 1L;

        doThrow(RuntimeException.class).when(userService).delete(expectedId);

        MvcResult result = this.mockMvc.perform(delete("/user/{id}", expectedId))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

}
