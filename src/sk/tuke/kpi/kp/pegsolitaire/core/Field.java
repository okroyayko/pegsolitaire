package sk.tuke.kpi.kp.pegsolitaire.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        java.nio.file.Path path = java.nio.file.Paths.get("src/sk/tuke/kpi/kp/pegsolitaire", filename);

        try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(path)) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null && row < rowCount) {
                for (int col = 0; col < columnCount; col++) {
                    TileState state;
                    if (col < line.length()) {
                        char ch = line.charAt(col);
                        switch (ch) {
<<<<<<< HEAD
                            case 'X': state = TileState.BLOCKED; break;
                            case 'O': state = TileState.PEG; break;
                            case '.': state = TileState.EMPTY; break;
                            case ' ': state = TileState.BLOCKED; break;
                            default:  state = TileState.BLOCKED;
=======
                            case 'X':
                                state = TileState.PEG;
                                break;
                            case 'O':
                                state = TileState.EMPTY;
                                break;
                            case ' ':
                                state = TileState.BLOCKED;
                                break;
                            default:
                                state = TileState.BLOCKED;
>>>>>>> 784c9fb (v1)
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

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
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
            System.out.println("Move not allowed");
            return;
        }

        int midR = (r1 + r2) / 2;
        int midC = (c1 + c2) / 2;

        tiles[r1][c1].setState(TileState.EMPTY);
        tiles[midR][midC].setState(TileState.EMPTY);
        tiles[r2][c2].setState(TileState.PEG);

        updateGameState();
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