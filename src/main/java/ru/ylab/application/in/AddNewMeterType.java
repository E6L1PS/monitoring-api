package ru.ylab.application.in;

/**
 * интерфейс для добавления нового типа счётчика.
 *
 * <p>Этот интерфейс предоставляет метод {@code execute}, который принимает
 * имя нового типа счётчика и выполняет необходимые действия для его добавления.</p>
 *
 * <p>Реализации этого интерфейса должны предоставить конкретную логику добавления
 * нового типа счётчика.</p>
 *
 * @author Pesternikov Danil
 */
public interface AddNewMeterType {

    /**
     * Выполняет добавление нового типа счётчика.
     *
     * @param name имя нового типа счётчика.
     */
    void execute(String name);
}