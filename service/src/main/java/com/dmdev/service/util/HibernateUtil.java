package com.dmdev.service.util;

import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = getConfiguration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addPackage("com.dmdev");
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(Request.class);
        configuration.addAnnotatedClass(Tariff.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(CarCharacteristic.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        return configuration;
    }

    public static Session getSession() {
        return buildSessionFactory().openSession();
    }
}
