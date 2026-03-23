package main.java.sk.tuke.kpi.kp.pegsolitaire.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import static main.java.sk.tuke.kpi.kp.pegsolitaire.consoleui.ConsoleUI.RED;
import static main.java.sk.tuke.kpi.kp.pegsolitaire.consoleui.ConsoleUI.RESET;

public class Field {
    private final int rowCount;
    private final int columnCount;
    private final Tile[][] tiles;
    private GameState gameState;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tiles = new Tile[rowCount][columnCount];
        this.gameState = GameState.PLAYING;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public GameState getGameState() {
        return gameState;
    }

    public void readFile(String filename) {
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream("levels/" + filename);

            if (in == null) {
                throw new RuntimeException("Level file not found: " + filename);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            int row = 0;

            while ((line = reader.readLine()) != null && row < rowCount) {
                for (int col = 0; col < columnCount; col++) {
                    TileState state;

                    if (col < line.length()) {
                        char ch = line.charAt(col);
                        switch (ch) {
                            case 'X' -> state = TileState.PEG;
                            case 'O' -> state = TileState.EMPTY;
                            case ' ' -> state = TileState.BLOCKED;
                            default -> state = TileState.BLOCKED;
                        }
                    } else {
                        state = TileState.BLOCKED;
                    }

                    tiles[row][col] = new Tile(state);
                }
                row++;
            }


            while (row < rowCount) {
                for (int col = 0; col < columnCount; col++) {
                    tiles[row][col] = new Tile(TileState.BLOCKED);
                }
                row++;
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading level: " + e.getMessage());
        }
    }

    public boolean isMoveAllowed(int r1, int c1, int r2, int c2) {
        if (!isInside(r1, c1) || !isInside(r2, c2)) return false;

        Tile start = tiles[r1][c1];
        Tile end = tiles[r2][c2];

        if (start.getState() != TileState.PEG) return false;
        if (end.getState() != TileState.EMPTY) return false;

        int dr = r2 - r1;
        int dc = c2 - c1;

        if (!((Math.abs(dr) == 2 && dc == 0) || (Math.abs(dc) == 2 && dr == 0))) {
            return false;
        }

        int midR = (r1 + r2) / 2;
        int midC = (c1 + c2) / 2;

        Tile middle = tiles[midR][midC];
        return middle.getState() == TileState.PEG;
    }

    public void move(int r1, int c1, int r2, int c2) {
        if (!isMoveAllowed(r1, c1, r2, c2)) {
            System.out.println(RED + "Invalid move!" + RESET);
            return;
        }

        int midR = (r1 + r2) / 2;
        int midC = (c1 + c2) / 2;

        tiles[r1][c1].setState(TileState.EMPTY);
        tiles[midR][midC].setState(TileState.EMPTY);
        tiles[r2][c2].setState(TileState.PEG);

        updateGameState();
    }

    public int getRemainingPegs() {
        int count = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (tiles[r][c].getState() == TileState.PEG) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isSolved() {
        int pegCount = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (tiles[r][c].getState() == TileState.PEG) {
                    pegCount++;
                    if (pegCount > 1) return false;
                }
            }
        }
        return pegCount == 1;
    }

    private void updateGameState() {
        if (isSolved()) {
            gameState = GameState.SOLVED;
            return;
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (tiles[r][c].getState() == TileState.PEG) {
                    if (isMoveAllowed(r, c, r - 2, c)) return;
                    if (isMoveAllowed(r, c, r + 2, c)) return;
                    if (isMoveAllowed(r, c, r, c - 2)) return;
                    if (isMoveAllowed(r, c, r, c + 2)) return;
                }
            }
        }

        gameState = GameState.FAILED;
    }

    private boolean isInside(int r, int c) {
        return r >= 0 && r < rowCount && c >= 0 && c < columnCount;
    }
}