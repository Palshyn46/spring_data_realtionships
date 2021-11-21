package com.example.spring_data_relationships.beanpostprocessor;

import com.example.spring_data_relationships.SpringDataRelationships;
import com.example.spring_data_relationships.annotation.BeanPostProcessorAnnotationMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DepartmentServiceBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var beanClass = bean.getClass();
        Collection<String> methodsWithAnnotation = Arrays.stream(beanClass.getMethods())
                .filter(method -> method.isAnnotationPresent(BeanPostProcessorAnnotationMethod.class))
                .map(Method::getName)
                .collect(Collectors.toList());
        if (!methodsWithAnnotation.isEmpty()) {
            Object proxy = Proxy.newProxyInstance(
                    SpringDataRelationships.class.getClassLoader(),
                    beanClass.getInterfaces(),
                    (o, method, objects) -> {
                        Object result = method.invoke(bean, objects);
                        if (methodsWithAnnotation.contains(method.getName())) {
                            MethodInvokeCache.putToCache(objects);
                        }
                        return result;
                    }
            );
            return proxy;
        }
        return bean;
    }
}