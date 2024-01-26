package ru.ylab;

import org.reflections.Reflections;
import ru.ylab.annotations.Singleton;
import ru.ylab.configs.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private final Config config;
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private final Reflections configuratorsScanner = new Reflections("ru.ylab.configurators");
    private ObjectFactory factory;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

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


    public Config getConfig() {
        return config;
    }

    public Reflections getConfiguratorsScanner() {
        return configuratorsScanner;
    }
}
