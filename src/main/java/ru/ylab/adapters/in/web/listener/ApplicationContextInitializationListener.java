package ru.ylab.adapters.in.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.ylab.Application;
import ru.ylab.ApplicationContext;

import java.util.HashMap;

/**
 * Слушатель инициализации контекста приложения.
 * Создан: 10.02.2024.
 *
 * @author Pesternikov Danil
 */
@WebListener
public class ApplicationContextInitializationListener implements ServletContextListener {

    /**
     * Контекст приложения.
     */
    public static ApplicationContext context;

    /**
     * Метод вызывается при инициализации сервлет контекста.
     *
     * @param sce событие инициализации контекста
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            context = Application.run("ru.ylab", new HashMap<>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод вызывается при уничтожении сервлет контекста.
     *
     * @param sce событие уничтожения контекста
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}