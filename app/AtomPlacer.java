package app;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class AtomPlacer {
    public static void placeAtom(Pane pane, double centerX, double centerY) {
        Circle atom = new Circle(centerX, centerY, Constants.ATOM_SIZE);
        atom.setFill(Color.RED);
        pane.getChildren().add(atom);
    }
}
