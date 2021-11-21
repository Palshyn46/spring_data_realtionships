package com.example.spring_data_relationships.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue addUser() {
        return new Queue("addUser");
    }

    @Bean
    public Queue addDepartment() {
        return new Queue("addDepartment");
    }

    @Bean
    public Queue addUserToDepartment() {
        return new Queue("addUserToDepartment");
    }

    @Bean
    public Queue beanPostProcessorQueue() {
        return new Queue("beanPostProcessorQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(addUser())
                .to(directExchange())
                .with("addUser");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(addDepartment())
                .to(directExchange())
                .with("addDepartment");
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(addUserToDepartment())
                .to(directExchange())
                .with("addUserToDepartment");
    }

    @Bean
    public Binding binding4() {
        return BindingBuilder.bind(beanPostProcessorQueue())
                .to(directExchange())
                .with("beanPostProcessorQueue");
    }
}
