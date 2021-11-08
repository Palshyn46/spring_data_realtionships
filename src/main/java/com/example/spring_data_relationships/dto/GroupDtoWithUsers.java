package com.example.spring_data_relationships.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDtoWithUsers {

    private Long id;
    private String name;
    private Set<UserDto> users;
}