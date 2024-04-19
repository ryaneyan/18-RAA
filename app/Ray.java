package app;

import static app.Constants.*;
import static app.HexagonButton.*;
import static app.RootPane.getAtoms;
import javafx.scene.control.Button;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<Point2D> rayPath = new ArrayList<>();
    private int lastXIndex;
    private int lastYIndex;
    private int[] hitAtomCoordinates = new int[2];
    private int finalHitState;

    public Ray(int X, int Y, RayDirection direction)
    {

            double centerX = HexBoard.getHexBoard().get(X).get(Y).getCentreX();
            double centerY = HexBoard.getHexBoard().get(X).get(Y).getCentreY();

        setFirstPosition(X, Y, direction);
        lastXIndex = X;
        lastYIndex = Y;

        // checks whether ray originates on an atom, if yes then instantly terminates it
        if (checkAtomAtPosition(X, Y)) {
            System.out.println("Ray Absorbed");
            finalHitState = 2;
            return;
        } else if (checkInitialReflection(X, Y, direction)) {
            System.out.println("Ray reflected");
            finalHitState = 3;
            return;
        }
//        rayPath.add(new Point2D(centerX, centerY));
//        checkAOFHit(X, Y, direction);
        extendRay(X, Y, direction);
    }

    private void extendRay(int xIndex, int yIndex, RayDirection direction) {



        try {
            Hexagon hex = HexBoard.getHexagon(xIndex, yIndex);
        } catch (IndexOutOfBoundsException exception) {
            setLastPosition(lastXIndex, lastYIndex, direction);

            extendRayFurther(direction,  8, 54);
            return;
        }


        Point2D currentPos = new Point2D(HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreX(), HexBoard.getHexBoard().get(xIndex).get(yIndex).getCentreY());
//        Point2D nextPos = new Point2D(HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreX(), HexBoard.getHexBoard().get(newXindex).get(newYindex).getCentreY());


        RayDirection nextDirection = direction; // Initialize with current direction
        rayPath.add(currentPos);
        int[] directHitCheckerCoords = determineNewIndexes(xIndex, yIndex, direction);
        int numAreasInHitAtom = HexBoard.getHexagon(hitAtomCoordinates[0], hitAtomCoordinates[1]).getContainsAreaOfInfluences();
        int numAreasInCurrentAtom = HexBoard.getHexagon(xIndex, yIndex).getContainsAreaOfInfluences();

        System.out.println("Current: " + xIndex + " " + yIndex);
        System.out.println("areas in current: " + numAreasInCurrentAtom);

        if (checkAtomAtPosition(directHitCheckerCoords[0], directHitCheckerCoords[1]) && numAreasInCurrentAtom == 1) {
            // If it's a direct hit, no further action is needed here. Log or handle as appropriate.
            System.out.println("Direct hit! Ray stops.");
            finalHitState = 2;

            return;
        } else if (checkAOFHit(xIndex, yIndex, direction)) {
            System.out.println("Hit:     " + hitAtomCoordinates[0] + " " + hitAtomCoordinates[1]);
            System.out.println("areas in hit: " + numAreasInHitAtom);
                switch (direction) {
                    case DIAGONAL_DOWN_LEFT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex && hitAtomCoordinates[1] == yIndex - 1) {
                                nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                            }
                            else
                                nextDirection = RayDirection.HORIZONTAL_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[3]) nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[5]) nextDirection = RayDirection.HORIZONTAL_RIGHT;
                            else nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                        break;
                    case DIAGONAL_UP_LEFT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex && hitAtomCoordinates[1] == yIndex - 1) {
                                nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                            }
                            else
                                nextDirection = RayDirection.HORIZONTAL_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[3]) nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[4]) nextDirection = RayDirection.HORIZONTAL_RIGHT;
                            else nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                        break;
                    case DIAGONAL_UP_RIGHT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex && hitAtomCoordinates[1] == yIndex + 1) {
                                nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                            }
                            else
                                nextDirection = RayDirection.HORIZONTAL_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[3]) nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[5]) nextDirection = RayDirection.HORIZONTAL_LEFT;
                            else nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                        break;
                    case DIAGONAL_DOWN_RIGHT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex && hitAtomCoordinates[1] == yIndex + 1)
                                nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                            else
                                nextDirection = RayDirection.HORIZONTAL_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[4]) nextDirection = RayDirection.HORIZONTAL_LEFT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[3]) nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                            else nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                        break;
                    case HORIZONTAL_RIGHT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex + 1)
                                nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                            else
                                nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[4]) nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[5]) nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                            else nextDirection = RayDirection.HORIZONTAL_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.HORIZONTAL_LEFT;
                        break;
                    case HORIZONTAL_LEFT:
                        if (numAreasInCurrentAtom == 1) {
                            if (hitAtomCoordinates[0] == xIndex + 1)
                                nextDirection = RayDirection.DIAGONAL_UP_LEFT;
                            else
                                nextDirection = RayDirection.DIAGONAL_DOWN_LEFT;
                        }
                        else if (numAreasInCurrentAtom == 2) {
                            if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[4]) nextDirection = RayDirection.DIAGONAL_DOWN_RIGHT;
                            else if (Hexagon.checkForAdjacentAtoms(hitAtomCoordinates[0], hitAtomCoordinates[1])[5]) nextDirection = RayDirection.DIAGONAL_UP_RIGHT;
                            else nextDirection = RayDirection.HORIZONTAL_RIGHT;
                        }
                        else if (numAreasInCurrentAtom == 3) nextDirection = RayDirection.HORIZONTAL_RIGHT;
                        break;
                    default:
                        return;
            }
        }

        int[] newIndexes = determineNewIndexes(xIndex, yIndex, nextDirection);

        lastXIndex = xIndex;
        lastYIndex = yIndex;
        System.out.println("----------------------------------");

        extendRay(newIndexes[0], newIndexes[1], nextDirection);
    }

    public int getFinalHitState() {
        return finalHitState;
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
        return new int[]{newXindex, newYindex};
    }

    private boolean checkAOFHit(int xIndex, int yIndex, RayDirection direction) {
        Hexagon current = HexBoard.getHexagon(xIndex, yIndex);
        Point2D point = new Point2D(current.getCentreX(), current.getCentreY());
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            if ((HexBoard.getHexBoard().get(getAtoms().get(2*i)).get(getAtoms().get(2*i+1)).getAreaOfInfluence() == null)) {
            }
            else if (HexBoard.getHexBoard().get(getAtoms().get(2 * i)).get(getAtoms().get(2 * i + 1)).getAreaOfInfluence().contains(point)) {
                int directHitCheck = checkDirectHit(xIndex, yIndex, direction);
//                System.out.println("hits atom at: " + getAtoms().get(2*i) + "," + getAtoms().get(2*i+1));

                hitAtomCoordinates[0] = getAtoms().get(2*i);
                hitAtomCoordinates[1] = getAtoms().get(2*i+1);

                return true;
            }
        }
        return false;
    }

    private int checkDirectHit(int xIndex, int yIndex, RayDirection direction) {
        for (int i = 0; i < getAtoms().size(); i += 2) {
            int atomXIndex = getAtoms().get(i);
            int atomYIndex = getAtoms().get(i + 1);

            switch (direction) {
                case HORIZONTAL_RIGHT:
                    if ((yIndex + 1 == atomYIndex && xIndex == atomXIndex) && !(checkAtomAtPosition(atomXIndex + 1, atomYIndex))) {
                        return 2;                    }
                    break;
                case HORIZONTAL_LEFT:
                    if ((yIndex - 1 == atomYIndex && xIndex == atomXIndex) && !(checkAtomAtPosition(atomXIndex - 1, atomYIndex))) {
                        return 2;                    }
                    break;
                case DIAGONAL_UP_RIGHT:
                    if (((xIndex > 4 && xIndex - 1 == atomXIndex && yIndex + 1 == atomYIndex) ||
                            (xIndex <= 4 && xIndex - 1 == atomXIndex && yIndex == atomYIndex)) && !(checkAtomAtPosition(atomXIndex, atomYIndex - 1))) {
                        return 2;                    }
                    break;
                case DIAGONAL_DOWN_RIGHT:
                    if (((xIndex < 4 && xIndex + 1 == atomXIndex && yIndex + 1 == atomYIndex) ||
                            (xIndex >= 4 && xIndex + 1 == atomXIndex && yIndex == atomYIndex)) && !(checkAtomAtPosition(atomXIndex, atomYIndex - 1))) {
                        return 2;                    }
                    break;
                case DIAGONAL_UP_LEFT:
                    if (((xIndex > 4 && xIndex - 1 == atomXIndex && yIndex == atomYIndex) ||
                            (xIndex <= 4 && xIndex - 1 == atomXIndex && yIndex - 1 == atomYIndex)) && !(checkAtomAtPosition(atomXIndex, atomYIndex + 1))) {
                        return 2;                    }
                    break;
                case DIAGONAL_DOWN_LEFT:
                    if (((xIndex < 4 && xIndex + 1 == atomXIndex && yIndex == atomYIndex) ||
                            (xIndex >= 4 && xIndex + 1 == atomXIndex && yIndex - 1 == atomYIndex)) && !(checkAtomAtPosition(atomXIndex, atomYIndex + 1) || !(checkAtomAtPosition(atomXIndex, atomYIndex - 1)))) {
                        return 2;
                    }
                    break;
            }
        }
        return 0;
    }
    void displayRay(Pane pane) {
        for (int i = 0; i < rayPath.size() - 1; i++) {
            //this for testing
//                 Line toAdd = new Line(rayPath.get(i).getX(), rayPath.get(i).getY(), rayPath.get(i).getX(), rayPath.get(i).getY());

            //actual ray drawer
            Line toAdd = new Line(rayPath.get(i).getX(), rayPath.get(i).getY(), rayPath.get(i + 1).getX(), rayPath.get(i + 1).getY());

            toAdd.setStroke(Color.DEEPSKYBLUE);
            toAdd.setStrokeWidth(3);
            toAdd.setVisible(false);
            pane.getChildren().add(toAdd);
        }
    }
    private void setFirstPosition(int X, int Y, RayDirection direction) {
        rayPath.add(getFirstPosition(X, Y, direction));
    }

    private Point2D getFirstPosition(int X, int Y, RayDirection direction) {
        Hexagon current = HexBoard.getHexagon(X, Y);
        Point2D[] points = current.getPointsArray();

        return switch (direction) {
            case HORIZONTAL_RIGHT -> getMidpoint(points[4], points[5]);
            case HORIZONTAL_LEFT -> getMidpoint(points[1], points[2]);
            case DIAGONAL_DOWN_LEFT -> getMidpoint(points[0], points[1]);
            case DIAGONAL_DOWN_RIGHT -> getMidpoint(points[5], points[0]);
            case DIAGONAL_UP_LEFT -> getMidpoint(points[2], points[3]);
            case DIAGONAL_UP_RIGHT -> getMidpoint(points[3], points[4]);
        };
    }

    private void setLastPosition(int X, int Y, RayDirection direction) {
        setFirstPosition(X, Y, getOppositeDirection(direction));
    }

    private RayDirection getOppositeDirection(RayDirection direction) {
        return switch (direction) {
            case HORIZONTAL_RIGHT -> RayDirection.HORIZONTAL_LEFT;
            case HORIZONTAL_LEFT -> RayDirection.HORIZONTAL_RIGHT;
            case DIAGONAL_DOWN_LEFT -> RayDirection.DIAGONAL_UP_RIGHT;
            case DIAGONAL_DOWN_RIGHT -> RayDirection.DIAGONAL_UP_LEFT;
            case DIAGONAL_UP_LEFT -> RayDirection.DIAGONAL_DOWN_RIGHT;
            case DIAGONAL_UP_RIGHT -> RayDirection.DIAGONAL_DOWN_LEFT;
        };
    }

    private String getStyleForDirection(RayDirection direction) {
        return switch (direction) {
            case HORIZONTAL_RIGHT -> "horizontal-right";
            case HORIZONTAL_LEFT -> "horizontal-left";
            case DIAGONAL_DOWN_LEFT -> "diagonal-down-left";
            case DIAGONAL_DOWN_RIGHT -> "diagonal-down-right";
            case DIAGONAL_UP_LEFT -> "diagonal-up-left";
            case DIAGONAL_UP_RIGHT -> "diagonal-up-right";
        };
    }

    private Point2D getMidpoint(Point2D start, Point2D end) {
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;
        return new Point2D(midX, midY);
    }

    private boolean checkAtomAtPosition(int xIndex, int yIndex) {
        try {
            HexBoard.getHexagon(xIndex, yIndex);
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
        return HexBoard.getHexagon(xIndex, yIndex).isAtom();
    }
    private boolean checkInitialReflection(int X, int Y, RayDirection direction) {
        Point2D point = getFirstPosition(X, Y, direction);
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            if (HexBoard.getHexBoard().get(getAtoms().get(2 * i)).get(getAtoms().get(2 * i + 1)).getAreaOfInfluence().contains(point)) {
                return true;
            }
        }
        return false;
    }

    public void extendRayFurther(RayDirection direction, double extensionLength, int buttons) {
        Point2D lastPoint = rayPath.get(rayPath.size() - 1);

        double newX = lastPoint.getX();
        double newY = lastPoint.getY();
        switch (direction) {
            case HORIZONTAL_RIGHT:
                newX += extensionLength;
                break;
            case HORIZONTAL_LEFT:
                newX -= extensionLength;
                break;
            case DIAGONAL_UP_LEFT:
                newX -= extensionLength;
                newY -= extensionLength;
                break;
            case DIAGONAL_UP_RIGHT:
                newX += extensionLength ;
                newY -= extensionLength;
                break;
            case DIAGONAL_DOWN_LEFT:
                newX -= extensionLength;
                newY += extensionLength;
                break;
            case DIAGONAL_DOWN_RIGHT:
                newX += extensionLength;
                newY += extensionLength;
                break;
        }
        Point2D newPoint = new Point2D(newX, newY);
//        rayPath.add(newPoint);
//    if(!(finalHitState == 0)) {
        Button hitButton = HexagonButton.getButtonAtPoint(newPoint, HexagonButton.root);
        if (hitButton != null) {
//            System.out.println("Ray hits button: " + hitButton);
            hitButton.getStyleClass().removeAll("horizontal-left", "horizontal-right", "diagonal-down-left", "diagonal-down-right", "diagonal-up-left", "diagonal-up-right");
            hitButton.getStyleClass().add("button-hit");
            hitButton.getStyleClass().add(getStyleForDirection(direction));
            hitButton.setDisable(true);
//            }
        }
    }
}
