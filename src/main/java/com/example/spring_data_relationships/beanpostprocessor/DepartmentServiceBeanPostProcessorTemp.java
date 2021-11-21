package com.example.spring_data_relationships.beanpostprocessor;

import com.example.spring_data_relationships.annotation.BeanPostProcessorAnnotationClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class DepartmentServiceBeanPostProcessorTemp implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(BeanPostProcessorAnnotationClass.class)) {
            Method[] qwe = bean.getClass().getDeclaredMethods();
            log.info(qwe.toString());
            log.info("----------------------------log---------------------------");
        }
        return bean;
    }
}
