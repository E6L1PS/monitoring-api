package ru.ylab.configurators;


import ru.ylab.ApplicationContext;
import ru.ylab.annotations.Autowired;

import java.lang.reflect.Field;

/**
 * Класс {@code AutowiredAnnotationObjectConfigurator} реализует интерфейс {@link ObjectConfigurator}
 * и предоставляет конфигурацию объектов, используя аннотацию {@link Autowired}.
 * <p>
 * Этот конфигуратор автоматически внедряет зависимости в поля объекта, помеченные аннотацией {@code Autowired}.
 * Зависимости извлекаются из контекста приложения.
 * </p>
 * <p>
 * Для использования этого конфигуратора добавьте аннотацию {@link Autowired} к полям, которые требуется автоматически внедрить, и зарегистрируйте этот конфигуратор в контексте приложения.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class AutowiredAnnotationObjectConfigurator implements ObjectConfigurator {

    /**
     * Конфигурирует объект, внедряя зависимости в поля с аннотацией {@link Autowired}.
     *
     * @param t       Объект для конфигурации.
     * @param context Контекст приложения, предоставляющий зависимости для внедрения.
     * @throws Exception Если произошла ошибка при конфигурации объекта.
     */
    @Override
    public void configure(Object t, ApplicationContext context) throws Exception {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
