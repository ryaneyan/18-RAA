package app;

import static app.Constants.*;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

enum RayDirection{
    HORIZONTAL_LEFT,
    HORIZONTAL_RIGHT,
    DIAGONAL_UP_LEFT,
    DIAGONAL_UP_RIGHT,
    DIAGONAL_DOWN_LEFT,
    DIAGONAL_DOWN_RIGHT
}

public class Ray {
    List<Point2D> rayPath = new ArrayList<>();

    public Ray(int X, int Y, RayDirection direction)
    {
        rayPath.add(new Point2D(HexBoard.getHexBoard().get(X).get(Y).getCentreX()-X_DIFF/2, HexBoard.getHexBoard().get(0).get(0).getCentreY()));
        rayPath.add(new Point2D(HexBoard.getHexBoard().get(X).get(Y).getCentreX(), HexBoard.getHexBoard().get(0).get(0).getCentreY()));
        extendRay(X, Y, direction);
    }

    private void extendRay(int xIndex, int yIndex, RayDirection direction) {
        int newXindex = determineNewIndexes(xIndex, yIndex, direction)[0];
        int newYindex = determineNewIndexes(xIndex, yIndex, direction)[1];

        try {
            HexBoard.getHexBoard().get(newXindex).get(newYindex);
        } catch (IndexOutOfBoundsException exception){
            return;
        }

        if (checkAOFHit(newXindex, newYindex) == 1) {
            // BOUNCE LOGIC
            return;
        }

        rayPath.add(new Point2D(HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreX(), HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreY()));

        extendRay(newXindex, newYindex, direction);
    }

    private int[] determineNewIndexes(int xIndex, int yIndex, RayDirection direction) {
        int newXindex = xIndex, newYindex = yIndex;

        if (direction == RayDirection.HORIZONTAL_RIGHT) {
            newXindex = xIndex;
            newYindex = yIndex + 1;
        } else if (direction == RayDirection.HORIZONTAL_LEFT) {
            newXindex = xIndex;
            newYindex = yIndex - 1;
        } else if (direction == RayDirection.DIAGONAL_UP_RIGHT) {

            if (xIndex < 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex;
            } else if (xIndex >= 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex + 1;
            }
        } else if (direction == RayDirection.DIAGONAL_DOWN_RIGHT) {
            if (xIndex < 4) {
                newXindex = xIndex + 1;
                newYindex = yIndex + 1;
            } else if (xIndex >= 4) {
                newXindex = xIndex + 1;
                newYindex = yIndex;
            }
        } else if (direction == RayDirection.DIAGONAL_UP_LEFT) {
            if (xIndex > 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex;
            } else if (xIndex < 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex - 1;
            }
        } else if (direction == RayDirection.DIAGONAL_DOWN_LEFT) {
            if (xIndex < 4) {
                newXindex = xIndex + 1;
                newYindex = yIndex;
            } else if (xIndex >= 4) {
                newXindex = xIndex + 1;
                newYindex = yIndex - 1;
            }
        }
        return new int[] {newXindex, newYindex};
    }

    private int checkAOFHit(int xIndex, int yIndex) {
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            if ((HexBoard.getHexBoard().get(RootPane.getAtoms().get(2*i)).get(RootPane.getAtoms().get(2*i+1)).getAreaOfInfluence() == null)) {
            } else if (new Line(rayPath.get(rayPath.size()-1).getX(), rayPath.get(rayPath.size()-1).getY(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreX(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreY()).intersects((HexBoard.getHexBoard().get(RootPane.getAtoms().get(2*i)).get(RootPane.getAtoms().get(2*i+1))).getAreaOfInfluence().getBoundsInLocal())) {
                System.out.println("hits atom at: " + RootPane.getAtoms().get(2*i) + "," + RootPane.getAtoms().get(2*i+1));
                return 1;
            }
        }
//        System.out.println("no hit");
        return 0;
    }

    private void displayRay(Pane pane) {
        for (int i = 0; i < rayPath.size()-1; i++) {
            Line toAdd = new Line(rayPath.get(i).getX(), rayPath.get(i).getY(), rayPath.get(i+1).getX(), rayPath.get(i+1).getY());
            toAdd.setStroke(Color.DEEPSKYBLUE);
            toAdd.setStrokeWidth(5);
            pane.getChildren().add(toAdd);
        }
    }


    // examples

    static double r=10;

    public static void drawButton1(Pane root)
    {
        Button button1 = new Button();
        button1.setLayoutX(490);
        button1.setLayoutY(HexBoard.getHexBoard().get(0).get(0).getCentreY()-10);

        button1.setOnAction(event -> {
            new Ray(0, 0, RayDirection.HORIZONTAL_RIGHT).displayRay(root);

        });
        root.getChildren().add(button1);


        button1.setShape(new Circle(r));
        button1.setMinSize(2*r, 2*r);
        button1.setMaxSize(2*r, 2*r);
    }

    public static void drawButton2(Pane root)
    {
        Button button2 = new Button();
        button2.setOnAction(event -> new Ray(0, 0, RayDirection.DIAGONAL_DOWN_RIGHT).displayRay(root));
        // everytime shift start x shift end x
        root.getChildren().add(button2);

        button2.setShape(new Circle(r));
        button2.setMinSize(2*r, 2*r);
        button2.setMaxSize(2*r, 2*r);
    }

    public static void drawButton3(Pane root)
    {
        Button button3 = new Button();
        button3.setLayoutX(20);

        button3.setOnAction(event -> new Ray(0, 0, RayDirection.DIAGONAL_DOWN_LEFT).displayRay(root));
        // evverytime shift start x shift end x

        root.getChildren().add(button3);

        button3.setShape(new Circle(r));
        button3.setMinSize(2*r, 2*r);
        button3.setMaxSize(2*r, 2*r);
    }

    //going to need 55 buttons

}
