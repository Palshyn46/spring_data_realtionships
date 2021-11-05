package com.example.spring_data_relationships.entity;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;

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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY
//            ,
//            cascade = CascadeType.ALL
    )
    /*@JoinColumn(
//            name = "id",
            referencedColumnName = "id"
//            ,
//            insertable = false,
//            updatable = false
    )*/
    private DepartmentEntity department;
}
