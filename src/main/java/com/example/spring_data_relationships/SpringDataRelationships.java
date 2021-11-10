package com.example.spring_data_relationships;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class SpringDataRelationships {
//    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRelationships.class, args);
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        factory.setPort(15678);
//        factory.setUsername("user1");
//        factory.setPassword("MyPassword");
//        channel.queueDeclare("products_queue", false, false, false, null);
//        String message = "product details";
//        channel.basicPublish("", "products_queue", null, message.getBytes());
//        channel.close();
//        connection.close();
    }
}
