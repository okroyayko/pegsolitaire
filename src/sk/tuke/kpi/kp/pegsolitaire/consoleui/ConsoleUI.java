package sk.tuke.kpi.kp.pegsolitaire.consoleui;

import sk.tuke.kpi.kp.pegsolitaire.core.Field;
import sk.tuke.kpi.kp.pegsolitaire.core.Tile;
import sk.tuke.kpi.kp.pegsolitaire.core.TileState;

public class ConsoleUI {
    private Field field;

    public ConsoleUI(Field field) {
        this.field = field;
    }
    public void show() {
        for (int r = 0; r < field.getRowCount(); r++){
            for (int c = 0; c < field.getColumnCount(); c++){
                Tile tile = field.getTile(r, c);
                if(tile == null){
                    System.out.println("Tile is null");
                    continue;
                }
                TileState state = tile.getState();
                char ch;
                switch(state){
                    case PEG:
                        ch = 'O';
                        break;
                    case BLOCKED:
                        ch = 'X';
                        break;
                    case EMPTY:
                        ch =  '.';
                        break;
                    default:
                        ch = '.';
                }
                System.out.println(ch);
            }
        System.out.println();
        }
    }
    public void play() {
        System.out.println("###    Welcome to PegSolitaire    ###");
        show();
    }
}