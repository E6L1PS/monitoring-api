package ru.ylab.adapters.in.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс, представляющий элемент меню.
 * Этот класс использует проект Lombok для автоматической генерации методов, таких как геттеры, сеттеры, toString и др.
 * Класс также содержит методы для выполнения действия элемента меню и отображения элемента в виде строки.
 *
 * @author Pesternikov Danil
 */
@Builder
@Data
@AllArgsConstructor
public class MenuItem {

    /**
     * Заголовок элемента меню.
     */
    private String title;

    /**
     * Действие, связанное с элементом меню.
     */
    private IAction action;

    /**
     * Ссылка на следующее меню, если оно есть.
     */
    private Menu nextMenu;

    /**
     * Метод для выполнения действия элемента меню.
     * Вызывает метод execute() объекта, реализующего интерфейс IAction.
     */
    public void doAction() {
        action.execute();
    }

    /**
     * Переопределенный метод toString() для отображения элемента меню в виде строки.
     *
     * @return Строковое представление элемента меню.
     */
    @Override
    public String toString() {
        return title + "\n";
    }
}