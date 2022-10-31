package com.dmdev.service.config;

import com.dmdev.service.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.dmdev.service")
public class ApplicationConfiguration {

    @Bean
    public Session session(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }
}
