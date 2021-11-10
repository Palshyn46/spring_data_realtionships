package com.example.spring_data_relationships.rabbitmq;

import com.example.spring_data_relationships.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

//    private final AmqpTemplate template;
    private final RabbitTemplate template;

    @Autowired
    public SampleController(RabbitTemplate template) {
        this.template = template;
    }

//    @PostMapping("/emit")
//    public ResponseEntity<String> emit(@RequestBody String message) {
//        logger.info("Emit to myQueue");
//        template.convertAndSend("myQueue", message);
//        return ResponseEntity.ok("Success emit to queue");
//    }

//    @PostMapping("/emit")
//    public ResponseEntity<String> emit(@RequestBody String message) {
//        logger.info("Emit to myQueue");
//        for (int index = 0; index < 10; index++) {
//            template.convertAndSend("myQueue", ThreadLocalRandom.current().nextInt());
//        }
//        return ResponseEntity.ok("Success emit to queue");
//    }

//    @PostMapping("/emit")
//    public ResponseEntity<String> emit(@RequestBody String message) {
//        logger.info("Emit to myQueue");
//        template.setExchange("common-exchange");
//        template.convertAndSend("myQueue", message);
//        return ResponseEntity.ok("Success emit to queue");
//    }

//    @PostMapping("/adduser")
//    public ResponseEntity<String> rabbitmq(@RequestBody Map<String, String> map) {
//        logger.info("Message sending");
//        template.setExchange("direct-exchange");
//        template.convertAndSend(map.get("key"), map.get("message"));
//        return ResponseEntity.ok("Message was sent");
//    }

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
