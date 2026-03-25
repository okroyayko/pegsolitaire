package main.java.sk.tuke.kpi.kp.pegsolitaire.consoleui;

import main.java.sk.tuke.kpi.kp.pegsolitaire.core.Field;
import main.java.sk.tuke.kpi.kp.pegsolitaire.core.GameState;
import main.java.sk.tuke.kpi.kp.pegsolitaire.core.TileState;
import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Comment;
import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Rating;
import main.java.sk.tuke.kpi.kp.pegsolitaire.entity.Score;
import main.java.sk.tuke.kpi.kp.pegsolitaire.service.*;




import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";


    private Field field;
    private final Scanner scanner = new Scanner(System.in);

    private final CommentService commentService = new CommentServiceJDBC();
    private final RatingService ratingService = new RatingServiceJDBC();
    private final ScoreService scoreService = new ScoreServiceJDBC();
    private String playerName = null;

    public void menu() {

        System.out.println(BRIGHT_PURPLE + "██████╗ ███████╗ ██████╗     ███████╗ ██████╗ ██╗      ██╗████████╗ █████╗ ██╗██████╗ ███████╗\n" +
                "██╔══██╗██╔════╝██╔════╝     ██╔════╝██╔═══██╗██║      ██║╚══██╔══╝██╔══██╗██║██╔══██╗██╔════╝\n" +
                "███████║█████╗  ██║   ██╗    ███████╗██║   ██║██║      ██║   ██║   ███████║██║██████╔╝█████╗  \n" +
                "██╔══██║██╔══╝  ██║   ██║    ╚════██║██║   ██║██║      ██║   ██║   ██╔══██║██║██╔══██╗██╔══╝  \n" +
                "██║  ██║███████╗╚██████╔╝    ███████║╚██████╔╝███████╗ ██║   ██║   ██║  ██║██║██║  ██║███████╗\n" +
                "╚═╝  ╚═╝╚══════╝ ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝ ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝\n");

        if (playerName == null) {
            System.out.print("Enter your name: ");
            playerName = scanner.nextLine().trim();
        }

        while (true) {
            System.out.println(BRIGHT_CYAN + "=== PEG SOLITAIRE ===" + RESET);
            System.out.println(BRIGHT_GREEN + "1 - Play" + RESET);
            System.out.println("2 - Show feedback");
            System.out.println("3 - Show average rating");
            System.out.println("4 - Show top scores");
            System.out.println(RED + "q - Quit" + RESET);
            System.out.print(BRIGHT_YELLOW + "Enter your choice: " + RESET);


            String choice = scanner.nextLine().trim();

            if (choice.equals("q")) return;

            switch (choice) {
                case "1": selectLevel(); break;
                case "2": showFeedback(); break;
                case "3": showAverageRating(); break;
                case "4": showTopScores(); break;
                default:
                    System.out.println(RED + "Invalid choice!" + RESET);
            }
        }
    }


    private void selectLevel() {
        while (true) {
            System.out.println(BRIGHT_PURPLE + "=== SELECT LEVEL ===" + RESET);
            System.out.println("1 - Level 1");
            System.out.println("2 - Level 2");
            System.out.println("3 - Level 3");
            System.out.println(RED + "b - Back" + RESET);
            System.out.print(BRIGHT_YELLOW + "Choose: " + RESET);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": loadLevel("level1.txt"); play(); return;
                case "2": loadLevel("level2.txt"); play(); return;
                case "3": loadLevel("level3.txt"); play(); return;
                case "b": return;
                default:
                    System.out.println(YELLOW + "Invalid choice!" + RESET);
            }
        }
    }



    private void loadLevel(String filename) {
        field = new Field(7, 7);
        field.readFile(filename);
    }

    private void leaveFeedback() {
        String name = playerName;

        System.out.print("Your comment: ");
        String text = scanner.nextLine();

        commentService.addComment(new Comment(name, "pegsolitaire", text, new Date()));

        System.out.println(GREEN + "Comment saved!" + RESET);
    }

    private void showFeedback() {
        var list = commentService.getComments("pegsolitaire");

        System.out.println(BRIGHT_GREEN + "COMMENTS" + RESET);
        for (Comment c : list) {
            System.out.println(c.getPlayer() + ": " + c.getComment());
        }
    }

    private void rateGame() {
        if (field.getGameState() == GameState.PLAYING) {
            System.out.println("Hru musíte najprv dokončiť, až potom môžete hodnotiť.");
            return;
        }

        String name = playerName;
        int rating;

        do {
            System.out.print(BLUE + "Zadajte hodnotenie (1-5): " + RESET);

            while (!scanner.hasNextInt()) {
                System.out.println(YELLOW + "Zadajte číslo od 1 do 5!" + RESET);
                scanner.next();
            }

            rating = scanner.nextInt();

            if (rating < 1 || rating > 5) {
                System.out.println(YELLOW + "Hodnotenie musí byť v rozsahu 1 až 5!" + RESET);
            }

        } while (rating < 1 || rating > 5);

        ratingService.setRating(new Rating(name, "pegsolitaire", rating));

        System.out.println("Rating saved!");
        scanner.nextLine();
    }



    private void showAverageRating() {
        int avg = ratingService.getAverageRating("pegsolitaire");
        System.out.println("Average rating: " + avg);
    }

    private void showTopScores() {
        var list = scoreService.getTopScores("pegsolitaire");

        System.out.println(BRIGHT_GREEN + "TOP SCORES" + RESET);
        for (Score s : list) {
            System.out.println(s.getPlayer() + " - " + s.getPoints());
        }
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

                switch (ch) {
                    case 'X' -> System.out.print(BRIGHT_YELLOW + "X " + RESET);
                    case 'O' -> System.out.print(BRIGHT_BLUE + "O " + RESET);
                    default  -> System.out.print(WHITE + ". " + RESET);
                }
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
                System.out.println(RED + "Invalid input!" + RESET);

                continue;
            }

            try {
                int r1 = Integer.parseInt(p[0]);
                int c1 = Integer.parseInt(p[1]);
                int r2 = Integer.parseInt(p[2]);
                int c2 = Integer.parseInt(p[3]);
                field.move(r1, c1, r2, c2);
            } catch (Exception e) {
                System.out.println(RED + "Invalid number!" + RESET);

            }
        }

        show();

        int remaining = field.getRemainingPegs();
        int points = 100 - remaining;



        if (field.getGameState() == GameState.SOLVED) {
            System.out.println(BRIGHT_GREEN + "You won!" + RESET);
        } else {
            System.out.println(RED +"No moves left. Game over." + RESET);
        }
        String name = playerName;


        scoreService.addScore(new Score(name, "pegsolitaire", points, new Date()));

        System.out.println("Score saved! Your score: " + points);

        System.out.println("Chcete zanechať komentár? (y/n)");

        String c;
        while (true) {
            c = scanner.nextLine().trim().toLowerCase();

            if (c.equals("y") || c.equals("n")) {
                break;
            }

            System.out.println("Neplatný vstup. Zadajte iba 'y' alebo 'n'.");
        }

        if (c.equals("y")) {
            leaveFeedback();
        }


        int existingRating = ratingService.getRating(playerName, "pegsolitaire");

        if (existingRating == 0) {
            System.out.println("Chcete ohodnotiť hru? (y/n)");
            String r = scanner.nextLine().trim();
            if (r.equalsIgnoreCase("y")) {
                rateGame();
            }
        } else {
            System.out.println("Už ste hodnotili hru. Ďalšie hodnotenie nie je potrebné.");
        }
    }
}