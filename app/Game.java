package app;

import static app.Constants.*;
import static app.WelcomePane.generateWelcomePane;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.Random;

public class Game extends Application {

    @Override
    public void start(Stage stage) {

        Pane welcomePane = WelcomePane.generateWelcomePane();
        Scene main = new Scene(welcomePane); // Adjust width and height as needed

        welcomePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // generates the board and buttons at the same time
                HexBoard.generateBoard();
                Pane rootPane = RootPane.generateRootPane();
                HexagonButton.createButtons(rootPane);
                NumberedHexagonButton.createButtons(rootPane);

                Button toggleButton = createVisibilityButton(rootPane);
                rootPane.getChildren().add(toggleButton);

                main.setRoot(rootPane);
            }
        });
        main.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Load the CSS file

        stage.setScene(main);
        stage.setMaximized(true);

        stage.setTitle("BlackBox+");
        stage.show();
    }

    public static void toggleVisibility(Pane pane)
    {
        for (javafx.scene.Node node : pane.getChildren()) {

            if (node instanceof Hexagon || node instanceof Button || node instanceof Text)
            {
                continue;
            }
            node.setVisible(!node.isVisible());
        }
    }

    public static Button createVisibilityButton(Pane pane)
    {
        Button toggleButton = new Button("Visibility");
        toggleButton.setOnAction(event -> toggleVisibility(pane));
        return toggleButton;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
