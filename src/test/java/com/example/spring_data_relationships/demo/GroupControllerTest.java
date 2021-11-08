package com.example.spring_data_relationships.demo;

import com.example.spring_data_relationships.controller.GroupController;
import com.example.spring_data_relationships.dto.GroupDto;
import com.example.spring_data_relationships.exceptions.MyEntityNotFoundException;
import com.example.spring_data_relationships.service.GroupService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach //?
    public void init() {
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfGroupAbsent() {
        this.mockMvc.perform(get("/group/1"))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn200IfGroupExist() {
        Long expectedId = 1L;
        String expectedName = "name";
        GroupDto testGroup = GroupDto.builder().id(expectedId).name(expectedName).build();

        when(groupService.get(eq(expectedId))).thenReturn(Optional.of(testGroup));

        MvcResult result = this.mockMvc.perform(get("/group/{id}", expectedId)).andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        GroupDto expectedGroup = objectMapper.readValue(content, GroupDto.class);
        assertEquals(expectedId, expectedGroup.getId());
        assertEquals(expectedName, expectedGroup.getName());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndGroupWithIdWhenCreateGroup() {
        String expectedName = "name";
        Long expectedId = 1L;
        GroupDto testGroup = GroupDto.builder().id(expectedId).name(expectedName).build();

        when(groupService.create(any(GroupDto.class))).thenReturn(testGroup);

        MvcResult result = this.mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGroup)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        GroupDto actualGroup = objectMapper.readValue(content, GroupDto.class);
        assertEquals(expectedId, actualGroup.getId());
        assertEquals(expectedName, actualGroup.getName());
    }

    @Test
    @SneakyThrows
    public void shouldReturn201AndUpdatedGroupWhenUpdate() {
        Long expectedId = 1L;
        String expectedName = "name";
        GroupDto testGroup = GroupDto.builder().id(expectedId).name(expectedName).build();

        when(groupService.update(any(GroupDto.class), eq(expectedId))).thenReturn(Optional.of(testGroup));

        MvcResult result = this.mockMvc.perform(put("/group/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGroup)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        GroupDto actualGroup = objectMapper.readValue(content, GroupDto.class);
        assertEquals(actualGroup.getId(), 1);
        assertEquals(expectedName, actualGroup.getName());
    }

    @Test
    @SneakyThrows //?
    public void shouldReturn404IfGroupNotExistWhenUpdate() {
        Long expectedId = 1L;
        String expectedName = "name";
        GroupDto testGroup = GroupDto.builder().id(expectedId).name(expectedName).build();
        this.mockMvc.perform(put("/group/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGroup)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn404IfGroupNotExistRemove() {
        Long expectedId = 1L;
        doThrow(MyEntityNotFoundException.class).when(groupService).delete(expectedId);
        this.mockMvc.perform(delete("/group/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldReturn204IfGroupExistAndRemove() {
        Long expectedId = 1L;
        this.mockMvc.perform(delete("/group/{id}", expectedId))
                .andExpect(status().isNoContent());
        verify(groupService, times(1)).delete(expectedId);
    }

    @Test
    @SneakyThrows
    public void shouldReturn400AndCustomMessageIfUnexpectedErrorDuringGet() {
        Long expectedId = 1L;
        String errorMessage = "Something went wrong";

        doThrow(RuntimeException.class).when(groupService).get(expectedId);

        MvcResult result = this.mockMvc.perform(get("/group/{id}", expectedId)
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
        GroupDto testGroup = GroupDto.builder().name("name").build();

        doThrow(RuntimeException.class).when(groupService).create(any(GroupDto.class));

        MvcResult result = this.mockMvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGroup)))
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
        GroupDto testGroup = GroupDto.builder().name("name").build();

        doThrow(RuntimeException.class).when(groupService).update(any(GroupDto.class), eq(expectedId));

        MvcResult result = this.mockMvc.perform(put("/group/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testGroup)))
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

        doThrow(RuntimeException.class).when(groupService).delete(expectedId);

        MvcResult result = this.mockMvc.perform(delete("/group/{id}", expectedId))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        assertEquals(errorMessage, response.get("message"));
    }

}
