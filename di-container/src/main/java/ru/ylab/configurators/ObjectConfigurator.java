package ru.ylab.configurators;


import ru.ylab.ApplicationContext;

public interface ObjectConfigurator {

    void configure(Object t, ApplicationContext context) throws Exception;
}
