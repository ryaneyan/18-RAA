package app;

import static app.Constants.*;
import static app.WelcomePane.generateWelcomePane;

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

        Pane welcomePane = WelcomePane.generateWelcomePane();
        Scene welcomeScene = new Scene(welcomePane); // Adjust width and height as needed
        stage.setScene(welcomeScene);
        stage.setMaximized(true);

        welcomeScene.setOnMouseClicked(e -> {
// generates the board and buttons at the same time
            HexBoard.generateBoard();
            Pane rootPane = RootPane.generateRootPane();
            HexagonButton.createButtons(rootPane);

            Scene mainScene = new Scene(rootPane); // Adjust width and height as needed

            stage.setScene(mainScene);
            stage.setMaximized(true);

        });


        stage.setTitle("BlackBox+");
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
