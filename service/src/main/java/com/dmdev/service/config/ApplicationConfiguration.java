package com.dmdev.service.config;

import com.dmdev.service.util.HibernateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@ComponentScan(basePackages = "com.dmdev.service")
public class ApplicationConfiguration {

    @Bean
    public EntityManager entityManager() {
        return HibernateUtil.getSession();
    }
}
