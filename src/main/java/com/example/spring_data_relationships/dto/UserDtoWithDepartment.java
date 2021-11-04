package com.example.spring_data_relationships.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoWithDepartment {

    private Long id;
    private String name;
    private String email;
    private Long departmentId;

    private DepartmentDto department;
}
