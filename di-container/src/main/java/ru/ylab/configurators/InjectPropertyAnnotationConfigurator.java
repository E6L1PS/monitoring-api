package ru.ylab.configurators;

import ru.ylab.ApplicationContext;
import ru.ylab.annotations.ConfigProperty;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс {@code InjectPropertyAnnotationConfigurator} реализует интерфейс {@link ObjectConfigurator}
 * и предоставляет конфигурацию объектов, используя аннотацию {@link ConfigProperty}.
 * <p>
 * Этот конфигуратор загружает настройки из файла "application.properties" и применяет их к полям объекта, помеченным аннотацией {@code ConfigProperty}.
 * </p>
 * <p>
 * Для использования этого конфигуратора добавьте аннотацию {@link ConfigProperty} к полям, которые требуется конфигурировать, и зарегистрируйте этот конфигуратор в контексте приложения.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class InjectPropertyAnnotationConfigurator implements ObjectConfigurator {


    /**
     * Настройки, загруженные из файла "application.properties".
     */
    private final Properties properties = new Properties();

    /**
     * Конструктор, загружающий настройки из файла "application.properties".
     */
    public InjectPropertyAnnotationConfigurator() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("application.properties");) {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Конфигурирует объект, применяя значения из файла "application.properties" к полям с аннотацией {@link ConfigProperty}.
     *
     * @param t       Объект для конфигурации.
     * @param context Контекст приложения, предоставляющий дополнительные данные для конфигурации (не используется в данном конфигураторе).
     */
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
