package ru.ylab;

import org.reflections.Reflections;
import ru.ylab.annotations.Singleton;
import ru.ylab.configs.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс {@code ApplicationContext} представляет контекст приложения, содержащий конфигурацию и кэш объектов.
 * Он также предоставляет доступ к фабрике объектов {@link ObjectFactory} и сканеру конфигураторов {@link Reflections}.
 * <p>
 * Контекст приложения позволяет получать объекты определенного типа, учитывая конфигурации и возможность использования кэша.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class ApplicationContext {

    /**
     * Конфигурация приложения, содержащая настройки.
     */
    private final Config config;

    /**
     * Кэш объектов для повторного использования.
     */
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();

    /**
     * Сканер конфигураторов, используемый для поиска классов конфигураторов.
     */
    private final Reflections configuratorsScanner = new Reflections("ru.ylab.configurators");

    /**
     * Фабрика объектов, используемая для создания и конфигурации объектов.
     */
    private ObjectFactory factory;

    /**
     * Создает экземпляр {@code ApplicationContext} с указанной конфигурацией.
     *
     * @param config Конфигурация приложения.
     */
    public ApplicationContext(Config config) {
        this.config = config;
    }

    /**
     * Устанавливает фабрику объектов для использования в контексте.
     *
     * @param factory Фабрика объектов.
     */
    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    /**
     * Получает объект указанного типа из контекста приложения. Возможно использование кэша для повторного использования объектов.
     *
     * @param type Тип объекта.
     * @param <T>  Тип объекта.
     * @return Объект указанного типа.
     * @throws Exception Если произошла ошибка при создании или конфигурации объекта.
     */
    public <T> T getObject(Class<T> type) throws Exception {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }

        return t;
    }

    /**
     * Получает конфигурацию приложения.
     *
     * @return Конфигурация приложения.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Получает сканер конфигураторов.
     *
     * @return Сканер конфигураторов.
     */
    public Reflections getConfiguratorsScanner() {
        return configuratorsScanner;
    }
}
