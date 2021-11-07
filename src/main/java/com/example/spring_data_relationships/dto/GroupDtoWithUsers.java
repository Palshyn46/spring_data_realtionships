package com.example.spring_data_relationships.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDtoWithUsers {

    private Long id;
    private String name;
    private Set<UserDto> users;
}
