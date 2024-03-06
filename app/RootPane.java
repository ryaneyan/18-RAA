package app;

import javafx.css.Size;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static app.Constants.ATOMS_AMOUNT;
import static app.Constants.RADIUS;

public class RootPane {
    private RootPane(){
    }
    private static List<Integer> atoms = new ArrayList<>();

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
        int x = 0, y = 1;
        while(tally < ATOMS_AMOUNT)
        {
//            int x = rand.nextInt(9);
//            int y = rand.nextInt(HexBoard.getHexBoard().get(x).size());
            int[] atomsTest = {1, 1, 8, 3, 6, 3, 4, 0, 5, 3, 7, 2};
            int j = atomsTest[x];
            int k = atomsTest[y];

            Hexagon current = HexBoard.getHexBoard().get(j).get(k);
            if (current.isAtom()) {
                continue;
            }
            atoms.add(j);
            atoms.add(k);
            current.convertToAtom(root);

//                System.out.println("X: " + centerX + " Y: " + centerY); //just for debugging purposes

            tally++;
            x+=2;
            y+=2;
        }
    }

    public static List<Integer> getAtoms() {
        return atoms;
    }
}
