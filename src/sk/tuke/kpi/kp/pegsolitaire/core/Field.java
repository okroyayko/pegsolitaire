package sk.tuke.kpi.kp.pegsolitaire.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Field {
    private int rowCount;
    private int columnCount;
    private Tile[][] tiles;
    private GameState gameState;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tiles = new Tile[rowCount][columnCount];
        this.gameState = GameState.PLAYING;
    }

    public void readFile(String filename) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
            if (is == null) {
                System.out.println("File not found: " + filename);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null && row < rowCount) {
                for (int col = 0; col < Math.min(line.length(), columnCount); col++) {
                    char ch = line.charAt(col);
                    TileState state;
                    switch (ch) {
                        case 'X':
                            state = TileState.BLOCKED;
                            break;
                        case 'O':
                            state = TileState.PEG;
                            break;
                        case '.':
                            state = TileState.EMPTY;
                            break;
                        default:
                            state = TileState.BLOCKED;
                    }
                    tiles[row][col] = new Tile(state);
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean isMoveAllowed(int r1, int c1, int r2, int c2) {
        return false;
    }

    public void move(int r1, int c1, int r2, int c2) {
    }

    public boolean isSolved() {
        return false;
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
}