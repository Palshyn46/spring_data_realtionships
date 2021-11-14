package com.example.spring_data_relationships.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "create_user_information")
public class CreateUserInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "enter_time")
    private String enterTime;
    private String ip;
    private String body;
    @JoinColumn(name = "exit_time")
    private String exitTime;
    private String response;
}
