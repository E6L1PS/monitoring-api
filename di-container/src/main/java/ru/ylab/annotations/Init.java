package ru.ylab.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация {@code Init} используется для пометки методов, которые должны быть вызваны при инициализации объекта.
 * <p>
 * Методы, помеченные этой аннотацией, будут вызваны после создания объекта, но до его использования.
 * </p>
 * <p>
 * Аннотация сохраняется в рантайме, что позволяет использовать ее в процессе выполнения программы.
 * </p>
 *
 * @author Pesternikov Danil
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Init {

}
