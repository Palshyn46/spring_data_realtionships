package com.example.spring_data_relationships.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDtoWithUserDto {

    private Long id;
    private String name;

    private List<UserDto> users;
}
