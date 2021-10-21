package com.example.spring_data_relationships.demo;

import com.example.spring_data_relationships.controller.DepartmentController;
import com.example.spring_data_relationships.dto.DepartmentDto;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfDepartmentAbsent() {
        this.mockMvc.perform(get("/department/1"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn200IfDepartmentExist() {
        Long expectedId = 1L;
        String expectedName = "name";
        DepartmentDto testDepartment = DepartmentDto.builder().id(expectedId).name(expectedName).build();

        when(departmentService.get(eq(expectedId))).thenReturn(Optional.of(testDepartment));

        MvcResult result = this.mockMvc.perform(get("/department/{id}", expectedId)).andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DepartmentDto expectedDepartment = objectMapper.readValue(content, DepartmentDto.class);
        assertEquals(expectedId, expectedDepartment.getId());
        assertEquals(expectedName, expectedDepartment.getName());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndDepartmentWithIdWhenCreateDepartment() {
        String expectedName = "name";
        Long expectedId = 1L;
        DepartmentDto testDepartment = DepartmentDto.builder().name(expectedName).build();

        when(departmentService.create(any(DepartmentDto.class)))
                .thenReturn(
                        DepartmentDto.builder()
                                .id(expectedId)
                                .name(expectedName)
                                .build()
                );

        MvcResult result = this.mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDepartment)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DepartmentDto actualDepartment = objectMapper.readValue(content, DepartmentDto.class);
        assertEquals(expectedId, actualDepartment.getId());
        assertEquals(expectedName, actualDepartment.getName());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndUpdatedDepartmentWhenUpdate() {
        Long expectedId = 1L;
        String expectedName = "name";
        DepartmentDto testDepartment = DepartmentDto.builder().id(expectedId).name(expectedName).build();

        when(departmentService.update(any(DepartmentDto.class), eq(expectedId)))
                .thenReturn(Optional.of(
                        DepartmentDto.builder()
                                .id(expectedId)
                                .name(expectedName).build())
                );

        MvcResult result = this.mockMvc.perform(put("/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDepartment)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DepartmentDto actualDepartment = objectMapper.readValue(content, DepartmentDto.class);
        assertEquals(actualDepartment.getId(), 1);
        assertEquals(expectedName, actualDepartment.getName());
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfDepartmentNotExistWhenUpdate() {
        Long excpectedId = 1L;
        String expectedName = "name";
        DepartmentDto testDepartment = DepartmentDto.builder().id(excpectedId).name(expectedName).build();
        this.mockMvc.perform(put("/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDepartment)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfDepartmentNotExistRemove() {
        Long expectedId = 1L;
        doThrow(MyEntityNotFoundException.class).when(departmentService).delete(expectedId);
        this.mockMvc.perform(delete("/department/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn204IfDepartmentExistAndRemove() {
        Long expectedId = 1L;
        this.mockMvc.perform(delete("/department/{id}", expectedId))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).delete(expectedId);
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringGet() {
        Long expectedId = 1L;
        String errorMessage = "Something went wrong";

        doThrow(RuntimeException.class).when(departmentService).get(expectedId);

        MvcResult result = this.mockMvc.perform(get("/department/{id}", expectedId)
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
        DepartmentDto testDepartment = DepartmentDto.builder().name("name").build();

        doThrow(RuntimeException.class).when(departmentService).create(any(DepartmentDto.class));

        MvcResult result = this.mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDepartment)))
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
        DepartmentDto testDepartment = DepartmentDto.builder().name("name").build();

        doThrow(RuntimeException.class).when(departmentService).update(any(DepartmentDto.class), eq(expectedId));

        MvcResult result = this.mockMvc.perform(put("/department/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDepartment)))
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

        doThrow(RuntimeException.class).when(departmentService).delete(expectedId);

        MvcResult result = this.mockMvc.perform(delete("/department/{id}", expectedId))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }
}
