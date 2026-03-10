package sk.tuke.kpi.kp.pegsolitaire.core;

import sk.tuke.kpi.kp.pegsolitaire.consoleui.ConsoleUI;

public class PegSolitaire {
    public static void main(String[] args) {
        Field field = new Field(7, 7); // або readFile()
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
}