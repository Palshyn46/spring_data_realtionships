package com.example.spring_data_relationships.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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


//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "users_groups_table",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<GroupEntity> groups = new HashSet<>();
}
