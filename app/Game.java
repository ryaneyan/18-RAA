package app;

import static app.Constants.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;

public class Game extends Application {
    List<List<Hexagon>> hexBoard = new ArrayList<>();
    @Override
    public void start(Stage stage) {


        int limit = 5;
        boolean increasing = true;
        for (int i = 0; i < 9; i++) {
            hexBoard.add(new ArrayList<>());
            for (int j = 0; j < limit; j++) {
                if(increasing) hexBoard.get(i).add(new Hexagon(X_ORIGIN - (X_DIFF/2*i) + (X_DIFF*j), Y_ORIGIN + Y_DIFF*i, RADIUS));
                else hexBoard.get(i).add(new Hexagon(X_ORIGIN - (X_DIFF/2*(8-i)) + (X_DIFF*j), Y_ORIGIN + Y_DIFF*i, RADIUS));

            }
            if (i == 4) increasing = false;
            if (increasing) limit++;
            else limit--;
        }

        Pane welcome = new Pane();
        welcome.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        javafx.scene.text.Text welcomeText = new javafx.scene.text.Text("Welcome to BlackBox+\nPress anywhere to enter");

        welcomeText.setFill(Color.YELLOW);
        welcomeText.setFont(Font.font("Lucida Console", 40));
        welcomeText.setLayoutX(500);
        welcomeText.setLayoutY(450);
        welcome.getChildren().add(welcomeText);

        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        for (List<Hexagon> list: hexBoard) {
            root.getChildren().addAll(list);
        }

        Scene main = new Scene(welcome);
        stage.setScene(main);
        stage.setMaximized(true);
        welcome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                main.setRoot(root);
                double centerX = hexBoard.get(0).get(1).getCentreX();
                double centerY = hexBoard.get(0).get(1).getCentreY();

                System.out.println("X: " + centerX + " Y: " + centerY); //just for debugging purposes


                placeAtom(root, centerX, centerY, Color.RED);
            }
        });
        stage.setMaximized(true);
        stage.setTitle("BlackBox+");
        stage.show();
        System.out.println(hexBoard.get(0).get(0).getPoints());
    }

    private void placeAtom(Pane pane, double centerX, double centerY, Color color) {
        Circle atom = new Circle(centerX, centerY, Atom_Size);
        atom.setFill(color);
        pane.getChildren().add(atom);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
