package ru.ylab;

import ru.ylab.annotations.Init;
import ru.ylab.configurators.ObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ObjectFactory {

    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) throws Exception {
        this.context = context;

        for (Class<? extends ObjectConfigurator> aClass : context.getConfiguratorsScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getConstructor().newInstance());
        }
    }

    public <T> T createObject(Class<T> implClass) throws Exception {

        T t = implClass.getDeclaredConstructor().newInstance();

        configureAll(t);

        invokeInit(implClass, t);

        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws InvocationTargetException, IllegalAccessException {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configureAll(T t) {
        configurators.forEach(objectConfigurator -> {
            try {
                objectConfigurator.configure(t, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
