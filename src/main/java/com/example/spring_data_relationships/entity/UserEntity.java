package com.example.spring_data_relationships.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Column(name = "department_id")
    private Long departmentId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "department_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    private DepartmentEntity department;

}
