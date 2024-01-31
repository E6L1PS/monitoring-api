package ru.ylab.adapters.in.view;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс MenuView представляет визуальный компонент для отображения и взаимодействия с меню приложения.
 * Он использует Navigator для навигации по меню и выполнения действий, связанных с выбранными пунктами меню.
 *
 * @author Pesternikov Danil
 */
@Singleton
@NoArgsConstructor
public class MenuView {

    /**
     * Компонент навигатора для взаимодействия с меню.
     */
    @Autowired
    private Navigator navigator;

    /**
     * Метод run() запускает цикл взаимодействия с меню до момента выбора выхода из приложения.
     * Выводит текущее меню, ожидает ввода пользователя, обрабатывает введенные данные и выполняет соответствующее действие.
     */
    public void run() {
        boolean exit = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!exit) {
                navigator.printMenu();
                try {
                    String line = reader.readLine();
                    int choice = Integer.parseInt(line);

                    MenuItem item = navigator.getCurrentMenu().getMenuItems().get(choice);

                    navigator.navigate(choice);
                    item.doAction();
                    exit = navigator.getExit();
                } catch (NumberFormatException e) {
                    System.out.println("Введите цифру пункта");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Такого пункта нет");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}