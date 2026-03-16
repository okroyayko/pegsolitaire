package sk.tuke.kpi.kp.pegsolitaire.consoleui;

import sk.tuke.kpi.kp.pegsolitaire.core.*;

import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("=== PEG SOLITAIRE ===");
            System.out.println("1 - Level 1");
            System.out.println("2 - Level 2");
            System.out.println("3 - Level 3");
            System.out.println("q - Quit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("q")) return;

            switch (choice) {
                case "1": loadLevel("level1.txt"); break;
                case "2": loadLevel("level2.txt"); break;
                case "3": loadLevel("level3.txt"); break;
                default:
                    System.out.println("Invalid choice");
                    continue;
            }

            play();
        }
    }

    private void loadLevel(String filename) {
        field = new Field(7, 7);
        field.readFile(filename);
    }

    private void show() {
        System.out.println();
        System.out.print("    ");
        for (int c = 0; c < field.getColumnCount(); c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        System.out.print("   ");
        for (int i = 0; i < field.getColumnCount() * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int r = 0; r < field.getRowCount(); r++) {
            System.out.print(r + " | ");
            for (int c = 0; c < field.getColumnCount(); c++) {

                TileState s = field.getTile(r, c).getState();

                char ch = switch (s) {
                    case PEG -> 'X';
                    case EMPTY -> 'O';
                    case BLOCKED -> ' ';
                };

                System.out.print(ch + " ");
            }
            System.out.println("|");
        }

        System.out.print("   ");
        for (int i = 0; i < field.getColumnCount() * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.println("\n");
    }

    public void play() {
        while (field.getGameState() == GameState.PLAYING) {
            show();
            System.out.print("Move (r1 c1 r2 c2) or q: ");
            String line = scanner.nextLine().trim();

            if (line.equals("q")) return;

            String[] p = line.split("\\s+");
            if (p.length != 4) {
                System.out.println("Invalid input");
                continue;
            }

            try {
                int r1 = Integer.parseInt(p[0]);
                int c1 = Integer.parseInt(p[1]);
                int r2 = Integer.parseInt(p[2]);
                int c2 = Integer.parseInt(p[3]);
                field.move(r1, c1, r2, c2);
            } catch (Exception e) {
                System.out.println("Invalid numbers");
            }
        }

        show();

        if (field.getGameState() == GameState.SOLVED)
            System.out.println("You won!");
        else
            System.out.println("No moves left. Game over.");
    }
}