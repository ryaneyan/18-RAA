package app;

import javafx.css.Size;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.Random;

import static app.Constants.ATOMS_AMOUNT;
import static app.Constants.RADIUS;

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

        Ray.drawButton1(rootPane);
        Ray.drawButton2(rootPane);
        Ray.drawButton3(rootPane);

        // the end x and y work from the start x and y so rather than wanting to go to a coordinate you should instead add or subract what x/y you are away from the original
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
