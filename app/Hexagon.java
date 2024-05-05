package app;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import java.util.*;

import static app.Constants.*;

public class Hexagon extends Polygon {

    private static List<Point2D> clickedCoordinates = new ArrayList<>();
    private static Map<Circle, Point2D> circleMap = new HashMap<>();

    private int containsAreaOfInfluences = 0;

    public Hexagon(double centreX, double centreY, double radius) {
        super();

        this.centreX = centreX;
        this.centreY = centreY;

        double angle = Math.PI;
        final double angleDegreeChange = Math.PI * 2 / NUM_OF_SIDES + (240 * Math.PI / 180);

        for (int i = 0; i < NUM_OF_SIDES; i++, angle += angleDegreeChange) {
            double x = Math.sin(angle) * radius + centreX;
            double y = Math.cos(angle) * radius + centreY;
            pointsArray[i] = new Point2D(x, y);
            super.getPoints().addAll(x, y);
        }
        super.setStroke(Color.YELLOW);
        super.setStrokeWidth(3);
        super.setOnMouseEntered(mouseEvent -> {
            Hexagon.super.toFront();
            Hexagon.super.setStroke(Color.RED);
        });
        super.setOnMouseExited(mouseEvent -> {
            Hexagon.super.toBack();
            Hexagon.super.setStroke(Color.YELLOW);
        });
        super.setOnMouseClicked(mouseEvent -> {
            Point2D currentPoint = new Point2D(centreX, centreY);
            Optional<Circle> existingCircle = circleMap.keySet().stream()
                    .filter(circle -> circleMap.get(circle).equals(currentPoint))
                    .findFirst();

            if (existingCircle.isPresent()) {
                ((Pane) this.getParent()).getChildren().remove(existingCircle.get());
                circleMap.remove(existingCircle.get());
                clickedCoordinates.remove(currentPoint);
            } else if (clickedCoordinates.size() < ATOMS_AMOUNT) {
                clickedCoordinates.add(currentPoint);

                Circle circle = new Circle(centreX, centreY, ATOM_SIZE, Color.RED);
                ((Pane) this.getParent()).getChildren().add(circle);
                this.getParent().requestLayout();

                circleMap.put(circle, currentPoint);
            }
        });
    }

    private Point2D[] pointsArray = new Point2D[6];
    final private double centreX;
    final private double centreY;
    private boolean isAtom = false;
    private Circle areaOfInfluence = null;

    public double getCentreX() {
        return centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public Circle getAreaOfInfluence() {
        return areaOfInfluence;
    }

    public boolean isAtom() {
        return isAtom;
    }

    public Point2D[] getPointsArray() {
        return pointsArray;
    }

    public void convertToAtom(Pane pane) {
        isAtom = true;
        Circle atomCircle = new Circle(centreX, centreY, ATOM_SIZE, Color.YELLOW);
        atomCircle.setVisible(false);
        pane.getChildren().add(atomCircle);
        setAreaOfInfluence(pane);
    }

    private void setAreaOfInfluence(Pane pane) {
        areaOfInfluence = new Circle(centreX, centreY, X_DIFF);
        areaOfInfluence.setFill(Color.TRANSPARENT);
        areaOfInfluence.setStroke(Color.BLUE);
        areaOfInfluence.setStrokeType(StrokeType.OUTSIDE);
        areaOfInfluence.getStrokeDashArray().addAll(5d, 10d);
        areaOfInfluence.setVisible(false);
        pane.getChildren().add(areaOfInfluence);
    }

    public static int checkForAtomAndChangeColor() {
        int correctGuesses = 0;
        for (Map.Entry<Circle, Point2D> entry : circleMap.entrySet()) {
            Circle circle = entry.getKey();
            Point2D clickedCoordinate = entry.getValue();
            boolean atomGuessedCorrectly = false;
            for (List<Hexagon> hexRow : HexBoard.getHexBoard()) {
                for (Hexagon hex : hexRow) {
                    if (hex.isAtom() && clickedCoordinate.equals(new Point2D(hex.getCentreX(), hex.getCentreY()))) {
                        circle.setFill(Color.GREEN);
                        atomGuessedCorrectly = true;
                        break;
                    }
                }
                if (atomGuessedCorrectly) {
                    correctGuesses++;
                    break;
                }
            }
        }
        return correctGuesses;
    }

    public void setAreaOfInfluenceCount() {
        for (int i = 0; i < ATOMS_AMOUNT; i++) {
            int atomX = RootPane.getAtoms().get(2 * i);
            int atomY = RootPane.getAtoms().get(2 * i + 1);

            if (HexBoard.getHexagon(atomX, atomY).getAreaOfInfluence().contains(centreX, centreY)) containsAreaOfInfluences++;
        }
    }

    public int getContainsAreaOfInfluences() {
        return containsAreaOfInfluences;
    }

    public static boolean[] checkForAdjacentAtoms(int x, int y) {
        boolean[] bordersAtoms = new boolean[6];
        int[][] indexes = new int[6][2];
        if (x < 4) {
            indexes = new int[][]{{x - 1, y - 1}, {x - 1, y}, {x, y - 1}, {x, y + 1}, {x + 1, y}, {x + 1, y + 1}};
        } else if (x > 4) {
            indexes = new int[][]{{x - 1, y}, {x - 1, y + 1}, {x, y - 1}, {x, y + 1}, {x + 1, y - 1}, {x + 1, y}};
        } else {
            indexes = new int[][]{{x - 1, y - 1}, {x - 1, y}, {x, y - 1}, {x, y + 1}, {x + 1, y - 1}, {x + 1, y}};
        }
        for (int i = 0; i < 6; i++) {
            try {
                bordersAtoms[i] = HexBoard.getHexagon(indexes[i][0], indexes[i][1]).isAtom;
            } catch (IndexOutOfBoundsException exception) {
                continue;
            }
        }
        return bordersAtoms;
    }
}
