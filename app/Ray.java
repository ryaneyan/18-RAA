package app;

import static app.Constants.*;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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

//            rayPath.add(new Point2D(centerX - X_DIFF/2, centerY));
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

        Point2D currentPos = new Point2D(HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreX(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreY());
        Point2D nextPos = new Point2D(HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreX(), HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreY());

        rayPath.add(new Point2D(HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreX(), HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreY()));

        if (checkAOFHit(newXindex, newYindex, direction) == 1) {
            switch (direction) {
                case DIAGONAL_DOWN_LEFT:
                    direction = RayDirection.DIAGONAL_DOWN_RIGHT;
                    break;
                case DIAGONAL_UP_LEFT:
                    direction = RayDirection.DIAGONAL_UP_RIGHT;
                    break;
                case DIAGONAL_UP_RIGHT:
                    direction = RayDirection.DIAGONAL_UP_LEFT;
                    break;
                case DIAGONAL_DOWN_RIGHT:
                    direction = RayDirection.DIAGONAL_DOWN_LEFT;
                    break;
                default:
                    // Optionally handle unexpected direction
                    return;
            }
        }
        else if (checkAOFHit(newXindex, newYindex, direction) == 2) {
            // If it's a direct hit, no further action is needed here. Log or handle as appropriate.
            System.out.println("Direct hit! Ray stops.");
            return;
        }

        rayPath.add(nextPos);
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

            if (xIndex > 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex + 1;
            } else if (xIndex <= 4) {
                newXindex = xIndex - 1;
                newYindex = yIndex;
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
            } else if (xIndex <= 4) {
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

    private int checkAOFHit(int xIndex, int yIndex, RayDirection direction) {
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            if ((HexBoard.getHexBoard().get(RootPane.getAtoms().get(2*i)).get(RootPane.getAtoms().get(2*i+1)).getAreaOfInfluence() == null)) {
            }
            else if (new Line(rayPath.get(rayPath.size()-1).getX(), rayPath.get(rayPath.size()-1).getY(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreX(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreY()).intersects((HexBoard.getHexBoard().get(RootPane.getAtoms().get(2*i)).get(RootPane.getAtoms().get(2*i+1))).getAreaOfInfluence().getBoundsInLocal())) {
                int directHitCheck = checkDirectHit(xIndex, yIndex, direction);
                System.out.println("hits atom at: " + RootPane.getAtoms().get(2*i) + "," + RootPane.getAtoms().get(2*i+1));

                if (directHitCheck == 2) {
                    return 2;
                } else {
                    return 1;
                }

            }
        }
        return 0;
    }

    private int checkDirectHit(int xIndex, int yIndex, RayDirection direction) {
        for (int i = 0; i < RootPane.getAtoms().size(); i += 2) {
            int atomXIndex = RootPane.getAtoms().get(i);
            int atomYIndex = RootPane.getAtoms().get(i + 1);

            switch (direction) {
                case HORIZONTAL_RIGHT:
                    if (yIndex + 1 == atomYIndex && xIndex == atomXIndex) {
                        return 2;                    }
                    break;
                case HORIZONTAL_LEFT:
                    if (yIndex - 1 == atomYIndex && xIndex == atomXIndex) {
                        return 2;                    }
                    break;
                case DIAGONAL_UP_RIGHT:
                    if ((xIndex > 4 && xIndex - 1 == atomXIndex && yIndex + 1 == atomYIndex) ||
                            (xIndex <= 4 && xIndex - 1 == atomXIndex && yIndex == atomYIndex)) {
                        return 2;                    }
                    break;
                case DIAGONAL_DOWN_RIGHT:
                    if ((xIndex < 4 && xIndex + 1 == atomXIndex && yIndex + 1 == atomYIndex) ||
                            (xIndex >= 4 && xIndex + 1 == atomXIndex && yIndex == atomYIndex)) {
                        return 2;                    }
                    break;
                case DIAGONAL_UP_LEFT:
                    if ((xIndex > 4 && xIndex - 1 == atomXIndex && yIndex == atomYIndex) ||
                            (xIndex <= 4 && xIndex - 1 == atomXIndex && yIndex - 1 == atomYIndex)) {
                        return 2;                    }
                    break;
                case DIAGONAL_DOWN_LEFT:
                    if ((xIndex < 4 && xIndex + 1 == atomXIndex && yIndex == atomYIndex) ||
                            (xIndex >= 4 && xIndex + 1 == atomXIndex && yIndex - 1 == atomYIndex)) {
                        return 2;
                    }
                    break;
            }
        }

        return 0;
    }

    // may use this later
//    private void extendRayPath(int xIndex, int yIndex, RayDirection direction) {
//        // Add a point slightly beyond the point of impact to the ray's path
//        double extensionX = HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreX() + (3 * 20);
//        double extensionY = HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreY() + (3 * 0.1);
//        rayPath.add(new Point2D(extensionX, extensionY));
//    }

    void displayRay(Pane pane) {
        for (int i = 0; i < rayPath.size()-1; i++) {
            Line toAdd = new Line(rayPath.get(i).getX(), rayPath.get(i).getY(), rayPath.get(i+1).getX(), rayPath.get(i+1).getY());
            toAdd.setStroke(Color.DEEPSKYBLUE);
            toAdd.setStrokeWidth(5);
            pane.getChildren().add(toAdd);

//            if (!rayPath.isEmpty()) {
//                Point2D lastPoint = rayPath.get(rayPath.size() - 1);
//                System.out.println("Last Point: (" + lastPoint.getX() + ", " + lastPoint.getY() + ")");
//            }
        }
    }
}
