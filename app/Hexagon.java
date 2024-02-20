package app;

import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Hexagon extends Polygon {
    final double NUM_OF_SIDES = 6;

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
        super.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Hexagon.super.toFront();
                Hexagon.super.setStroke(Color.RED);
            }
        });
        super.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Hexagon.super.setStroke(Color.YELLOW);
            }
        });
    }
    final private double centreX;
    final private double centreY;
    final private double[] points = new double[12];

    public double getCentreX() {
        return centreX;
    }

    public double getCentreY() {
        return centreY;
    }
}
