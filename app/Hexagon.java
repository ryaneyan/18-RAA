package app;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.Constants.*;

public class Hexagon extends Polygon {

    public Hexagon(double centreX, double centreY, double radius) {
        super();

        this.centreX = centreX;
        this.centreY = centreY;

        double angle = Math.PI;
        final double angleDegreeChange = Math.PI * 2 / NUM_OF_SIDES + (240 * Math.PI/180);

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
}
