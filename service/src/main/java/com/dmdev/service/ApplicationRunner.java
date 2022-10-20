package com.dmdev.service;

import com.dmdev.service.config.ApplicationConfiguration;
import com.dmdev.service.dao.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            UserRepository userRepository = context.getBean(UserRepository.class);
            System.out.println(userRepository);
        }
    }
}
