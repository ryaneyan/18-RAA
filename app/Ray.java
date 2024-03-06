package app;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;



public class Ray extends Line
{

    public Ray(Pane root, int X1, int Y1, int X2, int Y2)
    {
        super(HexBoard.getHexBoard().get(X1).get(Y1).getCentreX(), HexBoard.getHexBoard().get(X1).get(Y1).getCentreY(),
                HexBoard.getHexBoard().get(X2).get(Y2).getCentreX(), HexBoard.getHexBoard().get(X2).get(Y2).getCentreY());

        // create and configure line using points

        super.setStroke(Color.rgb(96, 253, 253));
        super.setStrokeWidth(5);
        root.getChildren().add(this);
    }

    // examples

    static double r=10;

    public static void drawButton1(Pane root)
    {
        Button button1 = new Button();
        button1.setLayoutX(490);
        button1.setLayoutY(HexBoard.getHexBoard().get(0).get(0).getCentreY()-10);


        button1.setOnAction(event -> new Ray(root, 0, 0, 0, 4));
        root.getChildren().add(button1);


        button1.setShape(new Circle(r));
        button1.setMinSize(2*r, 2*r);
        button1.setMaxSize(2*r, 2*r);
    }

    public static void drawButton2(Pane root)
    {
        Button button2 = new Button();
        button2.setOnAction(event -> new Ray(root, 0, 0, 8, 4));
        // evverytime shift start x shift end x
        root.getChildren().add(button2);

        button2.setShape(new Circle(r));
        button2.setMinSize(2*r, 2*r);
        button2.setMaxSize(2*r, 2*r);
    }

    public static void drawButton3(Pane root)
    {
        Button button3 = new Button();
        button3.setLayoutX(20);

        button3.setOnAction(event -> new Ray(root, 0, 0, 4, 0));
        // evverytime shift start x shift end x

        root.getChildren().add(button3);

        button3.setShape(new Circle(r));
        button3.setMinSize(2*r, 2*r);
        button3.setMaxSize(2*r, 2*r);
    }

    //going to need 55 buttons

}
