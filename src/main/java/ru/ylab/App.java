package ru.ylab;

import ru.ylab.adapters.in.view.MenuView;

import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = Application.run("ru.ylab", new HashMap<>());
        MenuView menuView = context.getObject(MenuView.class);
        menuView.run();
    }
}
