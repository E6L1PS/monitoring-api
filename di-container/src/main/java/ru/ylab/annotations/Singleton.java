package ru.ylab.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация {@code Singleton} используется для пометки классов, которые должны создаваться в единственном экземпляре.
 * <p>
 * Классы, помеченные этой аннотацией, будут созданы и сконфигурированы только один раз, а затем будут использоваться повторно везде, где требуется этот тип объекта.
 * </p>
 * <p>
 * Аннотация сохраняется в рантайме, что позволяет использовать ее в процессе выполнения программы.
 * </p>
 *
 * @author Pesternikov Danil
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {

}
