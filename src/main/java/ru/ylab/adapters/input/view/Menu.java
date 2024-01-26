package ru.ylab.adapters.input.view;

import lombok.Data;
import ru.ylab.annotations.Init;

import java.util.List;

@Data
public class Menu {

    private String name;

    private List<MenuItem> menuItems;

    @Init
    public void init() {
        this.name = "МЕНЮ";
        this.menuItems = List.of(
                MenuItem.builder().title("0. <--- Выход").action(() -> System.out.println("Программа завершена")).build(),
                MenuItem.builder().title("0. <--- Авторизация").action(() -> System.out.println("Программа завершена")).build()
        );
    }

    @Override
    public String toString() {
        return "----------" + name + "----------\n" +
                menuItems.toString().replaceAll(", |]|\\[", "");
    }
}