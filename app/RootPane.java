package app;

import javafx.css.Size;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static app.Constants.ATOMS_AMOUNT;

public class RootPane {
    private RootPane(){
    }
    private static List<Integer> atoms = new ArrayList<>();
    private static Pane rootPane = new Pane();

    public static void generateRootPane() {
        rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        for (List<Hexagon> list : HexBoard.getHexBoard()) {
            rootPane.getChildren().addAll(list);
        }
        generateRandomAtoms(rootPane);
    }

    private static void generateRandomAtoms(Pane root) {
        Random rand = new Random();
        int tally = 0;
        int j = 0, k = 1;
        int x, y;
        while (tally < ATOMS_AMOUNT)
        {
            x = rand.nextInt(9);
            y = rand.nextInt(HexBoard.getHexBoard().get(x).size());


//            int[] atomsTest = {3, 1, 4, 3, 8, 4, 8, 0, 8, 1, 8, 2};
//            x = atomsTest[j];
//            y = atomsTest[k];

            Hexagon current = HexBoard.getHexBoard().get(x).get(y);
            if (current.isAtom()) {
                continue;
            }
            atoms.add(x);
            atoms.add(y);

            current.convertToAtom(root);

            tally++;
            j+=2;
            k+=2;
        }
        orderAtoms(atoms);
    }

    public static Pane getRootPane() {
        return rootPane;
    }
    public static List<Integer> getAtoms() {
        return atoms;
    }

    private static void orderAtoms(List<Integer> atoms) {
        int[][] atomArray = new int[ATOMS_AMOUNT][2];
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            atomArray[i][0] = atoms.get(2*i);
            atomArray[i][1] = atoms.get(2*i+1);
        }

        int[] temp;
        for (int j = 0; j < ATOMS_AMOUNT-1; j++) {
            for (int i = 0; i < ATOMS_AMOUNT - 1; i++) {
                if (atomArray[i][0] > atomArray[i + 1][0]) {
                    temp = atomArray[i];
                    atomArray[i] = atomArray[i + 1];
                    atomArray[i + 1] = temp;
                } else if (atomArray[i][0] == atomArray[i + 1][0]) {
                    if (atomArray[i][1] > atomArray[i + 1][1]) {
                        temp = atomArray[i];
                        atomArray[i] = atomArray[i + 1];
                        atomArray[i + 1] = temp;
                    }
                }
            }
        }
        atoms.clear();
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            atoms.add(atomArray[i][0]);
            atoms.add(atomArray[i][1]);
        }

    }

    public static void setPlayerName(String name) {
        Text playerName = new Text(name);
        playerName.setX(1150);
        playerName.setY(320);
        playerName.setStyle("-fx-text-fill: yellow; -fx-font-size: 20");
        rootPane.getChildren().add(playerName);
    }
}
