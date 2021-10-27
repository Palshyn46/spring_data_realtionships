package com.example.spring_data_relationships.demo;

import com.example.spring_data_relationships.controller.UserController;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class Test {

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

    @org.junit.jupiter.api.Test
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

    @org.junit.Test
//    @org.junit.jupiter.api.Test
//    @SneakyThrows
    public void testGetById() throws Exception {
        //Optional<UserDto> userDto = userService.get(1L);
        //when(userService.get(eq(1L))).thenReturn(Optional.of(new UserDto()));

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

}
