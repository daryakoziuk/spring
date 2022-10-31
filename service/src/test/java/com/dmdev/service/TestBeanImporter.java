package com.dmdev.service;

import com.dmdev.service.dao.CarCharacteristicRepository;
import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dao.RequestRepository;
import com.dmdev.service.dao.TariffRepository;
import com.dmdev.service.dao.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestBeanImporter {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfigTest.class);

    public static SessionFactory getSessionFactory() {
        return context.getBean(SessionFactory.class);
    }

    public static UserRepository getUserRepository() {
        return context.getBean(UserRepository.class);
    }

    public static Session getSession() {
        return context.getBean(Session.class);
    }

    public static CarCharacteristicRepository getCarCharacteristicRepository() {
        return context.getBean(CarCharacteristicRepository.class);
    }

    public static CarRepository getCarRepository() {
        return context.getBean(CarRepository.class);
    }

    public static TariffRepository getTariffRepository() {
        return context.getBean(TariffRepository.class);
    }

    public static RequestRepository getRequestRepository() {
        return context.getBean(RequestRepository.class);
    }
}
