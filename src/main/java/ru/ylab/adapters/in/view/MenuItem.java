package ru.ylab.adapters.in.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MenuItem {

    private String title;
    private IAction action;
    private Menu nextMenu;

    public void doAction() {
        action.execute();
    }

    @Override
    public String toString() {
        return title + "\n";
    }
}