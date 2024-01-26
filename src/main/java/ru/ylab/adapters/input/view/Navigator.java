package ru.ylab.adapters.input.view;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Init;
import ru.ylab.annotations.Singleton;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Singleton
@NoArgsConstructor
public class Navigator {

    private boolean exitValue;
    @Autowired
    private Menu rootMenu;
    private Menu currentMenu;
    private Deque<Menu> menuStack;

    @Init
    public void init() {
        this.currentMenu = rootMenu;
        this.menuStack = new ArrayDeque<>(List.of(rootMenu));
        this.exitValue = false;
    }

    public void printMenu() {
        System.out.println(currentMenu);
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

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

    public boolean getExit() {
        return exitValue;
    }
}