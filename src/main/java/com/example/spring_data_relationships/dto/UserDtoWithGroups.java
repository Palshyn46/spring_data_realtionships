package com.example.spring_data_relationships.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoWithGroups {

    private Long id;
    private String name;
    private String email;
    private Set<GroupDto> groups;
}
