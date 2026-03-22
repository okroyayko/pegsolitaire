package main.java.sk.tuke.kpi.kp.pegsolitaire.consoleui;

import main.java.sk.tuke.kpi.kp.pegsolitaire.core.Field;
import main.java.sk.tuke.kpi.kp.pegsolitaire.core.GameState;
import main.java.sk.tuke.kpi.kp.pegsolitaire.core.TileState;
import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.CommentService;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.CommentServiceJDBC;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.RatingService;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.RatingServiceJDBC;

import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {
    private Field field;
    private final Scanner scanner = new Scanner(System.in);

    private final CommentService commentService = new CommentServiceJDBC();
    private final RatingService ratingService = new RatingServiceJDBC();

    public void menu() {
        while (true) {
            System.out.println("=== PEG SOLITAIRE ===");
            System.out.println("1 - Level 1");
            System.out.println("2 - Level 2");
            System.out.println("3 - Level 3");
            System.out.println("4 - Leave feedback");
            System.out.println("5 - Show feedback");
            System.out.println("6 - Rate game");
            System.out.println("7 - Show average rating");
            System.out.println("q - Quit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("q")) return;

            switch (choice) {
                case "1": loadLevel("level1.txt");
                    play();
                case "2": loadLevel("level2.txt");
                    play();
                case "3": loadLevel("level3.txt");
                    play();
                case "4": leaveFeedback();
                case "5": showFeedback();
                case "6": rateGame();
                case "7": showAverageRating();
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void loadLevel(String filename) {
        field = new Field(7, 7);
        field.readFile(filename);
    }

    private void leaveFeedback() {
        System.out.print("Your name: ");
        String name = scanner.nextLine();

        System.out.print("Your comment: ");
        String text = scanner.nextLine();

        commentService.addComment(new Comment(name, "pegsolitaire", text, new Date()));

        System.out.println("✔ Comment saved!");
    }

    private void showFeedback() {
        var list = commentService.getComments("pegsolitaire");

        System.out.println("=== COMMENTS ===");
        for (Comment c : list) {
            System.out.println(c.getPlayer() + ": " + c.getComment());
        }
    }

    private void rateGame() {
        System.out.print("Your name: ");
        String name = scanner.nextLine();

        System.out.print("Your rating (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());

        ratingService.setRating(new Rating(name, "pegsolitaire", rating));

        System.out.println("✔ Rating saved!");
    }

    private void showAverageRating() {
        int avg = ratingService.getAverageRating("pegsolitaire");
        System.out.println("Average rating: " + avg);
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