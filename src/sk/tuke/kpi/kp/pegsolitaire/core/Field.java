package sk.tuke.kpi.kp.pegsolitaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Field {

    private final Tile[][] tiles;
    private final int rows;
    private final int cols;

    public Field(String levelFile) {
        this.rows = 7;
        this.cols = 7;
        this.tiles = new Tile[rows][cols];
        loadLevel(levelFile);
    }

    public int getRowCount() {
        return rows;
    }

    public int getColumnCount() {
        return cols;
    }

    public Tile getTile(int r, int c) {
        return tiles[r][c];
    }

    private void loadLevel(String filename) {
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            if (is == null) {
                throw new RuntimeException("Level file not found: " + filename);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int r = 0; r < rows; r++) {
                String line = br.readLine();
                if (line == null) break;

                for (int c = 0; c < cols; c++) {
                    char ch = (c < line.length()) ? line.charAt(c) : ' ';

                    TileState state = switch (ch) {
                        case 'X' -> TileState.PEG;      
                        case 'O' -> TileState.EMPTY;    
                        case ' ' -> TileState.BLOCKED;  
                        default -> TileState.BLOCKED;
                    };

                    tiles[r][c] = new Tile(state);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading level file", e);
        }
    }

    public boolean move(int r1, int c1, int r2, int c2) {
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

        
        int mr = r1 + dr / 2;
        int mc = c1 + dc / 2;

        if (tiles[mr][mc].getState() != TileState.PEG) return false;

        
        start.setState(TileState.EMPTY);
        tiles[mr][mc].setState(TileState.EMPTY);
        end.setState(TileState.PEG);

        return true;
    }

    private boolean isInside(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}