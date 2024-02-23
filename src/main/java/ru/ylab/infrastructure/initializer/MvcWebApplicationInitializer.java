package ru.ylab.infrastructure.initializer;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.ylab.infrastructure.config.AppConfig;
import ru.ylab.infrastructure.config.JdbcConfig;
import ru.ylab.infrastructure.config.SecurityConfig;
import ru.ylab.infrastructure.config.WebConfig;

/**
 * Создан: 22.02.2024.
 *
 * @author Pesternikov Danil
 */
@Order(1)
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class, JdbcConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}