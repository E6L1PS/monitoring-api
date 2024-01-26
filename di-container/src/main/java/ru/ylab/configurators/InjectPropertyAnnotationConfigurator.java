package ru.ylab.configurators;

import ru.ylab.ApplicationContext;
import ru.ylab.annotations.ConfigProperty;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class InjectPropertyAnnotationConfigurator implements ObjectConfigurator {

    private final Properties properties = new Properties();

    public InjectPropertyAnnotationConfigurator() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("application.properties");) {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configure(Object t, ApplicationContext context) {

        Class<?> implClass = t.getClass();

        for (Field field : implClass.getDeclaredFields()) {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            if (annotation != null) {
                var value = annotation.value().isEmpty() ? properties.get(field.getName()) : properties.get(annotation.value());
                field.setAccessible(true);
                try {
                    field.set(t, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
