package com.example.spring_data_relationships.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

    private final RabbitTemplate template;

    @Autowired
    public RabbitMqController(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> addUser(@RequestBody String message) {
        template.setExchange("direct-exchange");
        template.convertAndSend("addUser", message);
        return ResponseEntity.ok("Message was sent");
    }

    @PostMapping("/adddepartment")
    public ResponseEntity<String> addDepartment(@RequestBody String message) {
        template.setExchange("direct-exchange");
        template.convertAndSend("addDepartment", message);
        return ResponseEntity.ok("Message was sent");
    }

    @PostMapping("/addusertodepartment")
    public ResponseEntity<String> addUserToDepartment(@RequestBody String message) {
        template.setExchange("direct-exchange");
        template.convertAndSend("addUserToDepartment", message);
        return ResponseEntity.ok("Message was sent");
    }
}
