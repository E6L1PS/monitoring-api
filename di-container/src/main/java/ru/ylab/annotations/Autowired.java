package ru.ylab.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация {@code Autowired} используется для пометки полей, которые должны быть автоматически внедрены зависимости.
 * <p>
 * Поля, помеченные этой аннотацией, будут автоматически заполнены значениями, полученными из контекста приложения.
 * </p>
 * <p>
 * Аннотация сохраняется в рантайме, что позволяет использовать ее в процессе выполнения программы.
 * </p>
 *
 * @author Pesternikov Danil
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

}
