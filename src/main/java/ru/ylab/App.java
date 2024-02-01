package ru.ylab;

import ru.ylab.adapters.in.view.MenuView;
import ru.ylab.adapters.out.persistence.util.ConnectionManager;

import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {

        try (var c = ConnectionManager.open()) {
            System.out.println(c.getTransactionIsolation());
        }
        ApplicationContext context = Application.run("ru.ylab", new HashMap<>());
        MenuView menuView = context.getObject(MenuView.class);
        menuView.run();
    }
}
