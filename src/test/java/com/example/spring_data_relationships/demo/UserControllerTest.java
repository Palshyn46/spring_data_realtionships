package com.example.spring_data_relationships.demo;


import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }


    @Test
    public void shouldReturn404IfUserAbsent() throws Exception {
        this.mockMvc.perform(get("/user/1")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200IfUserExist() throws Exception {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();

        when(userService.get(eq(expectedId))).thenReturn(testUser);

        MvcResult result = this.mockMvc.perform(get("/user/{id}", expectedId)).andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserDto expectedUser = objectMapper.readValue(content, UserDto.class);
        assertEquals( expectedId, expectedUser.getId());
        assertEquals( expectedName, expectedUser.getName());
        assertEquals(expectedEmail, expectedUser.getEmail() );
    }

    @Test
    public void shouldReturn201AndUserWithIdWhenCreateUser() throws Exception {
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

        when(userService.create(any(UserDto.class))).thenAnswer(I->{
            testUser.setId(1L);
            return testUser;
        });

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
    public void shouldReturn201AndUpdatedUserWhenUpdate() throws Exception {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();

        when(userService.update(any(UserDto.class), eq(expectedId))).thenReturn(UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build());

        MvcResult result = this.mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        UserDto actualUser = objectMapper.readValue(content, UserDto.class);
        assertEquals(actualUser.getId(), 1);
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    public void shouldReturn404IfUserNotExistWhenUpdate() throws Exception {
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();
        this.mockMvc.perform(put("/user/1")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404IfUserNotExistRemove() throws Exception {
        this.mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn204IfUserExistAndRemove() throws Exception {
        this.mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringGet() throws Exception {
        String errorMessage = "Something went wrong";
        MvcResult result = this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringPost() throws Exception {
        String errorMessage = "Something went wrong";
        MvcResult result = this.mockMvc.perform(post("/user").content("{}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringPut() throws Exception {
        String errorMessage = "Something went wrong";
        MvcResult result = this.mockMvc.perform(put("/user/1").content("{}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

    @Test
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringDelete() throws Exception {
        String errorMessage = "Something went wrong";
        MvcResult result = this.mockMvc.perform(delete("/user/1"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

}
