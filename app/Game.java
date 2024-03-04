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

    @Override
    public void start(Stage stage) {
        HexBoard.generateBoard();




        Pane welcome = WelcomePane.generateWelcomePane();
        Scene main = new Scene(welcome);
        stage.setScene(main);
        stage.setMaximized(true);


        stage.setMaximized(true);
        stage.setTitle("BlackBox+");
        stage.show();
//        System.out.println(hexBoard.get(0).get(0).getPoints());
    }



    public static void main(String[] args) {
        launch(args);
    }
}
