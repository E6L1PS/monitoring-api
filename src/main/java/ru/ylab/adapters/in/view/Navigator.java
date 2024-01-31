package ru.ylab.adapters.in.view;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Init;
import ru.ylab.annotations.Singleton;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Класс Navigator представляет навигатор для перемещения по меню в приложении.
 * Он отслеживает текущее меню, предоставляет методы для печати текущего меню, навигации между меню и проверки выхода.
 *
 * @author Pesternikov Danil
 */
@Singleton
@NoArgsConstructor
public class Navigator {

    /**
     * Флаг для определения выхода из приложения.
     */
    private boolean exitValue;

    /**
     * Корневое меню, с которого начинается навигация.
     */
    @Autowired
    private Menu rootMenu;

    /**
     * Текущее меню, в котором находится навигатор.
     */
    private Menu currentMenu;

    /**
     * Стек для отслеживания истории посещенных меню.
     */
    private Deque<Menu> menuStack;

    /**
     * Инициализация навигатора.
     * Устанавливает текущее меню в корневое меню и инициализирует стек меню.
     */
    @Init
    public void init() {
        this.currentMenu = rootMenu;
        this.menuStack = new ArrayDeque<>(List.of(rootMenu));
        this.exitValue = false;
    }

    /**
     * Выводит текущее меню в консоль.
     */
    public void printMenu() {
        System.out.println(currentMenu);
    }

    /**
     * Получает текущее меню.
     *
     * @return Текущее меню.
     */
    public Menu getCurrentMenu() {
        return currentMenu;
    }

    /**
     * Навигация по меню в зависимости от выбранного пункта меню.
     *
     * @param i Индекс выбранного пункта меню.
     */
    public void navigate(int i) {
        if (currentMenu.getMenuItems().get(i).getNextMenu() != null) {
            menuStack.push(currentMenu);
            currentMenu = currentMenu.getMenuItems().get(i).getNextMenu();
        }
        if (i == 0)
            if (currentMenu == rootMenu) {
                exitValue = true; //exit
            } else {
                currentMenu = menuStack.pop(); //back
            }
    }

    /**
     * Получает значение флага выхода.
     *
     * @return {@code true}, если необходимо выйти из приложения, иначе {@code false}.
     */
    public boolean getExit() {
        return exitValue;
    }
}