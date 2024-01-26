package ru.ylab.adapters.input.view;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Singleton
@NoArgsConstructor
public class MenuView {

    @Autowired
    private Navigator navigator;

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