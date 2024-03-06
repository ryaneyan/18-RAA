package app;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
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

            points[i] = Math.sin(angle) * radius + centreX;
            points[i*2+1] = Math.cos(angle) * radius + centreY;
            super.getPoints().addAll(points[i], points[i*2+1]);
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
    double[] points = new double[12];
    final private double centreX;
    final private double centreY;
    private boolean isAtom = false;

    public double getCentreX() {
        return centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public boolean isAtom() {
        return isAtom;
    }


    public void convertToAtom(Pane pane) {
        isAtom = true;
        pane.getChildren().add(new Circle(centreX, centreY, ATOM_SIZE, Color.RED));
        Circle areaOfInfluence = new Circle(centreX, centreY, X_DIFF);
        areaOfInfluence.setFill(Color.TRANSPARENT);
        areaOfInfluence.setStroke(Color.BLUE);
        areaOfInfluence.setMouseTransparent(true);
        pane.getChildren().add(areaOfInfluence);
    }
}
