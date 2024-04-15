package app;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.*;

import static app.Constants.*;

public class Hexagon extends Polygon {

    private static List<Point2D> clickedCoordinates = new ArrayList<>();
    private static Map<Circle, Point2D> circleMap = new HashMap<>();

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
        pane.getChildren().add(new Circle(centreX, centreY, ATOM_SIZE, Color.RED));
        setAreaOfInfluence(pane);
    }

    private void setAreaOfInfluence(Pane pane) {
        areaOfInfluence = new Circle(centreX, centreY, X_DIFF);
        areaOfInfluence.setFill(Color.TRANSPARENT);
        areaOfInfluence.setStroke(Color.BLUE);
        areaOfInfluence.setStrokeType(StrokeType.OUTSIDE); // Set the stroke type to outside

        areaOfInfluence.getStrokeDashArray().addAll(5d, 10d);
        pane.getChildren().add(areaOfInfluence);
    }

    public static void checkForAtomAndChangeColor() {
        for (Map.Entry<Circle, Point2D> entry : circleMap.entrySet()) {
            Circle circle = entry.getKey();
            Point2D clickedCoordinate = entry.getValue();
            for (List<Hexagon> hexRow : HexBoard.getHexBoard()) {
                for (Hexagon hex : hexRow) {
                    if (hex.isAtom() && clickedCoordinate.equals(new Point2D(hex.getCentreX(), hex.getCentreY()))) {
                        circle.setFill(Color.GREEN);
                    }
                }
            }
        }
    }
}
