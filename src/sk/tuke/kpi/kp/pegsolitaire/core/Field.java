package sk.tuke.kpi.kp.pegsolitaire.core;

public class Field {
    private int rowCount;
    private int columnCount;
    private Tile[][] tiles;
    private GameState gameState;

    public Field(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        this.tiles = new Tile[rows][columns];
        this.gameState = GameState.PLAYING;
    }

    public void readFile(String filename) {
    }

    public boolean isMoveAllowed(int r1, int c1, int r2, int c2) {
        return false;
    }

    public void move(int r1, int c1, int r2, int c2) {
        if (isMoveAllowed(r1, c1, r2, c2)) {
        }
    }

    public boolean isSolved() {
        return false;
    }
}