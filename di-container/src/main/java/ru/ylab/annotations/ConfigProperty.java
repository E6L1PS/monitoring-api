package ru.ylab.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация {@code ConfigProperty} используется для пометки полей, которые должны быть заполнены значениями из файла конфигурации.
 * Поля, помеченные этой аннотацией, будут заполнены значениями из файла "application.properties".
 * Если указано имя свойства, оно будет использовано в качестве ключа для поиска значения в файле конфигурации.
 * Если имя свойства не указано, используется имя поля в качестве ключа.
 * Аннотация сохраняется в рантайме, что позволяет использовать ее в процессе выполнения программы.
 *
 * @author Pesternikov Danil
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {

    /**
     * имя свойства в файле конфигурации. Если не указано, используется имя поля в качестве ключа.
     *
     * @return имя свойства.
     */
    String value() default "";
}
