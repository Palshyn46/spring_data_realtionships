package com.example.spring_data_relationships.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_groups_table")
public class UsersGroupsEntity {

    @Id
    @Column(name = "user_id")
    Long userId;
    @Column(name = "group_id")
    Long groupId;

}
