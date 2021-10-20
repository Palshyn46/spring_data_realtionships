package com.example.spring_data_relationships.demo;

import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
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

    }

}
