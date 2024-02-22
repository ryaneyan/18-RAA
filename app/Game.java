package app;

import static app.Constants.*;
import javafx.application.Application;
import javafx.scene.Scene;
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
import java.util.Random;

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

        Text welcomeText = new Text("Welcome to BlackBox+\nPress Anywhere to Start");

        welcomeText.setFill(Color.YELLOW);
        welcomeText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 40));
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
        welcome.setOnMouseClicked(mouseEvent -> {
            main.setRoot(root);

            Random rand = new Random();
            int tally = 0;
            while(tally < ATOMS_AMOUNT)
            {
                int x = rand.nextInt(9);
                int y = rand.nextInt(hexBoard.get(x).size());
                double centerX = hexBoard.get(x).get(y).getCentreX();
                double centerY = hexBoard.get(x).get(y).getCentreY();
                AtomPlacer.placeAtom(root, centerX, centerY);

                System.out.println("X: " + centerX + " Y: " + centerY); //just for debugging purposes

                tally++;
            }
        });

        stage.setMaximized(true);
        stage.setTitle("BlackBox+");
        stage.show();
        System.out.println(hexBoard.get(0).get(0).getPoints());
    }



    public static void main(String[] args) {
        launch(args);
    }
}
