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
    List<List<Hexagon>> hexBoard;
    @Override
    public void start(Stage stage) {

        hexBoard = Hexagon.generateBoard();


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
                Hexagon current = hexBoard.get(x).get(y);
                if (current.isAtom()) {
                    tally--;
                    continue;
                }
                current.convertToAtom(root);

//                System.out.println("X: " + centerX + " Y: " + centerY); //just for debugging purposes

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
