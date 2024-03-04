package app;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

import static app.Constants.ATOMS_AMOUNT;

public class RootPane {
    private RootPane(){
    }

    public static Pane generateRootPane() {
        Pane rootPane = new Pane();
        rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        for (List<Hexagon> list : HexBoard.getHexBoard()) {
            rootPane.getChildren().addAll(list);
        }
        generateRandomAtoms(rootPane);
        return rootPane;
    }

    private static void generateRandomAtoms(Pane root) {
        Random rand = new Random();
        int tally = 0;
        while(tally < ATOMS_AMOUNT)
        {
            int x = rand.nextInt(9);
            int y = rand.nextInt(HexBoard.getHexBoard().get(x).size());
            Hexagon current = HexBoard.getHexBoard().get(x).get(y);
            if (current.isAtom()) {
                tally--;
                continue;
            }
            current.convertToAtom(root);

//                System.out.println("X: " + centerX + " Y: " + centerY); //just for debugging purposes

            tally++;
        }
    }
}
