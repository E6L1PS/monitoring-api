package ru.ylab;

import ru.ylab.annotations.Init;
import ru.ylab.configurators.ObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code ObjectFactory} отвечает за создание объектов и выполнение необходимых конфигураций.
 * Он использует {@link ApplicationContext} для получения контекстной информации и список {@link ObjectConfigurator},
 * чтобы предоставить дополнительные задачи конфигурации.
 * <p>
 * Класс {@code ObjectFactory} следует паттерну Фабрика, предоставляя метод для создания объектов определенного типа.
 * Кроме того, он поддерживает инициализацию объектов через методы, аннотированные {@link Init}.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class ObjectFactory {


    /**
     * Контекст приложения, предоставляющий контекстную информацию для создания и конфигурации объектов.
     */
    private final ApplicationContext context;

    /**
     * Список конфигураторов объектов для применения дополнительных конфигураций во время создания объектов.
     */
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    /**
     * Конструирует {@code ObjectFactory} с указанным {@link ApplicationContext}.
     *
     * @param context Контекст приложения, который будет использоваться для...
     * @throws Exception Если произошла ошибка при создании экземпляров конфигураторов.
     */
    public ObjectFactory(ApplicationContext context) throws Exception {
        this.context = context;

        for (Class<? extends ObjectConfigurator> aClass : context.getConfiguratorsScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getConstructor().newInstance());
        }
    }

    /**
     * Создает объект указанного типа и выполняет необходимые конфигурации.
     *
     * @param implClass Тип объекта, который нужно создать.
     * @param <T>       Тип объекта.
     * @return Созданный и сконфигурированный объект.
     * @throws Exception Если произошла ошибка при создании или конфигурации объекта.
     */
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
