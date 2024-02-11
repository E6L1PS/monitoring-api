package ru.ylab.adapters.in.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.ylab.Application;
import ru.ylab.ApplicationContext;

import java.util.HashMap;

/**
 * Создан: 10.02.2024.
 *
 * @author Pesternikov Danil
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    public static ApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            context = Application.run("ru.ylab", new HashMap<>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}