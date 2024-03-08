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
import javafx.scene.control.Label;
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

            double centerX = HexBoard.getHexBoard().get(X).get(Y).getCentreX();
            double centerY = HexBoard.getHexBoard().get(X).get(Y).getCentreY();

            rayPath.add(new Point2D(centerX - X_DIFF/2, centerY));
            rayPath.add(new Point2D(centerX, centerY));
            extendRay(X, Y, direction);

    }

    private void extendRay(int xIndex, int yIndex, RayDirection direction) {
        int newXindex = determineNewIndexes(xIndex, yIndex, direction)[0];
        int newYindex = determineNewIndexes(xIndex, yIndex, direction)[1];

        try {
            Hexagon hex = HexBoard.getHexBoard().get(newXindex).get(newYindex);
        } catch (IndexOutOfBoundsException exception){
            return;
        }

        rayPath.add(new Point2D(HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreX(), HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreY()));

        if (checkAOFHit(newXindex, newYindex) == 1) {
            // Handle hit (e.g., bounce logic)
            return;
        }

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
        return 0;
    }

    void displayRay(Pane pane) {
        for (int i = 0; i < rayPath.size()-1; i++) {
            Line toAdd = new Line(rayPath.get(i).getX(), rayPath.get(i).getY(), rayPath.get(i+1).getX(), rayPath.get(i+1).getY());
            toAdd.setStroke(Color.DEEPSKYBLUE);
            toAdd.setStrokeWidth(5);
            pane.getChildren().add(toAdd);
        }
    }
}
