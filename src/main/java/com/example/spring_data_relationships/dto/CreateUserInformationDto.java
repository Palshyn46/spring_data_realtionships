package com.example.spring_data_relationships.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserInformationDto {

    public CreateUserInformationDto(String enterTime, String ip, String body) {
        this.enterTime = enterTime;
        this.ip = ip;
        this.body = body;
    }

    private Long id;
    private String enterTime;
    private String ip;
    private String body;
    private String exitTime;
    private String response;
}
