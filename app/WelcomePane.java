package app;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WelcomePane {
    private WelcomePane() {
    }

    public static Pane generateWelcomePane() {
        Pane welcomePane = new Pane();
        welcomePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Text welcomeText = new Text("Welcome to BlackBox+\nPress Anywhere to Start");

        welcomeText.setFill(Color.YELLOW);
        welcomeText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 40));
        welcomeText.setLayoutX(500);
        welcomeText.setLayoutY(450);
        welcomePane.getChildren().add(welcomeText);


        return welcomePane;
    }
}
