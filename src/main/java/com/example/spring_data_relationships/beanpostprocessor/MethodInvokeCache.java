package com.example.spring_data_relationships.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MethodInvokeCache {

    @Autowired
    private static RabbitTemplate template;

    public static void putToCache(Object result) {
        template.setExchange("direct-exchange");
        template.convertAndSend("beanPostProcessorQueue", result.toString());
    }

}
