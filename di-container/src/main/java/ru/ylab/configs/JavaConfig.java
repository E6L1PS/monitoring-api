package ru.ylab.configs;

import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    private final Reflections scanner;
    private final Map<Class, Class> ifcToImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifcToImplClass) {
        this.ifcToImplClass = ifcToImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifcToImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

            if (classes.size() != 1) {
                throw new RuntimeException(ifc + "Количество имплиментаций 0 или больше 1");
            }

            return classes.iterator().next();
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }

}
