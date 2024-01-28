package ru.ylab;


import ru.ylab.configs.Config;
import ru.ylab.configs.JavaConfig;

import java.util.Map;

/**
 * Класс {@code Application} предоставляет статический метод для запуска приложения.
 * Он инициализирует конфигурацию, контекст приложения и фабрику объектов, а затем возвращает контекст.
 * <p>
 * Для запуска приложения следует вызвать статический метод {@link #run(String, Map)} с указанными параметрами.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class Application {

    /**
     * Запускает приложение с заданными параметрами.
     *
     * @param packageToScan  Пакет для сканирования классов.
     * @param ifcToImplClass Соответствие интерфейсов и их реализаций.
     * @return Контекст приложения.
     * @throws Exception Если произошла ошибка при инициализации приложения.
     */
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifcToImplClass) throws Exception {
        Config config = new JavaConfig(packageToScan, ifcToImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);

        context.setFactory(objectFactory);

        return context;
    }
}
