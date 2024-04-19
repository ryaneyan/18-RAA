package app;

import java.util.ArrayList;
import java.util.List;

import static app.Constants.*;

public class HexBoard {

    private HexBoard() {
    }
    private static List<List<Hexagon>> hexBoard = new ArrayList<>();

    public static void generateBoard() {
        int limit = 5;
        boolean increasing = true;

        for (int i = 0; i < 9; i++) {
            hexBoard.add(new ArrayList<>());
            for (int j = 0; j < limit; j++) {
                Hexagon toAdd;
                if(increasing) {
                    toAdd = new Hexagon(X_ORIGIN - (X_DIFF / 2 * i) + (X_DIFF * j), Y_ORIGIN + Y_DIFF * i, RADIUS);
                }
                else {
                    toAdd = new Hexagon(X_ORIGIN - (X_DIFF / 2 * (8 - i)) + (X_DIFF * j), Y_ORIGIN + Y_DIFF * i, RADIUS);
                }
                hexBoard.get(i).add(toAdd);
            }
            if (i == 4) increasing = false;
            if (increasing) limit++;
            else limit--;
        }

    }

    public static List<List<Hexagon>> getHexBoard() {
        if (hexBoard == null) generateBoard();
        return hexBoard;
    }

    public static Hexagon getHexagon(int x, int y) {
        return hexBoard.get(x).get(y);
    }
}
