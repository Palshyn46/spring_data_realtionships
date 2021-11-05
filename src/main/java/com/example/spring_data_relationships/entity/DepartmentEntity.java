package com.example.spring_data_relationships.entity;

import com.example.spring_data_relationships.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department_table")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(
            fetch = FetchType.LAZY,
            orphanRemoval=true,
            cascade = CascadeType.ALL,
            mappedBy = "department")
    private List<UserEntity> users = new ArrayList<>();
}
