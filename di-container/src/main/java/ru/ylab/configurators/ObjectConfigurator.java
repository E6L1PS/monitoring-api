package ru.ylab.configurators;


import ru.ylab.ApplicationContext;

/**
 * интерфейс {@code ObjectConfigurator} предоставляет метод для конфигурации объекта.
 * <p>
 * Реализации этого интерфейса могут выполнять дополнительные настройки объекта в контексте приложения.
 * </p>
 * <p>
 * Для использования создайте класс, реализующий этот интерфейс, и определите необходимую логику в методе {@link #configure(Object, ApplicationContext)}.
 * </p>
 *
 * @author Pesternikov Danil
 */
public interface ObjectConfigurator {

    /**
     * Конфигурирует указанный объект в контексте приложения.
     *
     * @param t       Объект для конфигурации.
     * @param context Контекст приложения, предоставляющий дополнительные данные для конфигурации.
     * @throws Exception Если произошла ошибка при конфигурации объекта.
     */
    void configure(Object t, ApplicationContext context) throws Exception;
}
