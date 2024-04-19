package app;

import static app.Constants.*;
import static app.WelcomePane.generateWelcomePane;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Game extends Application {

    @Override
    public void start(Stage stage) {

        Pane welcomePane = WelcomePane.generateWelcomePane();
        Scene main = new Scene(welcomePane); // Adjust width and height as needed
        main.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("Start");
        startButton.setPrefSize(200,200);

        startButton.setOnAction(e -> {
            // generates the board and buttons at the same time
            HexBoard.generateBoard();
            Pane rootPane = RootPane.generateRootPane();

            System.out.println(Hexagon.checkForAdjacentAtoms(0, 4));

            for (Node hex: rootPane.getChildren()){
                if (hex instanceof Hexagon) {
                    Hexagon current = (Hexagon) hex;
                    current.setAreaOfInfluenceCount();
                }

            }
            HexagonButton.createButtons(rootPane);
            NumberedHexagonButton.createButtons(rootPane);

                Button toggleButton = createVisibilityButton(rootPane);
                rootPane.getChildren().add(toggleButton);

            Button checkAtomsButton = createCheckAtomsButton(rootPane);
            rootPane.getChildren().add(checkAtomsButton);
            checkAtomsButton.setLayoutX(1150);
            checkAtomsButton.setLayoutY(320);

            Button displayRayButton = displayRays(rootPane);
            rootPane.getChildren().add(displayRayButton);
            displayRayButton.setLayoutX(1150);
            displayRayButton.setLayoutY(420);

            ImageView coord = new ImageView();
            Image cord_sys = new Image("file:app/assets/coord.PNG");
            coord.setImage(cord_sys);
            coord.setFitWidth(910);
            coord.setFitHeight(640);
            coord.setLayoutX(210);
            coord.setLayoutY(70);
//            rootPane.getChildren().add(coord);

            main.setRoot(rootPane);
        });
        welcomePane.getChildren().add(startButton);

        startButton.layoutXProperty().bind(welcomePane.widthProperty().subtract(startButton.widthProperty()).divide(2));
        startButton.setLayoutY(600);

        stage.setScene(main);
        stage.setMaximized(true);
        stage.setTitle("BlackBox+");
        stage.show();
    }

    public static void toggleVisibility(Pane pane)
    {
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof Hexagon || node instanceof Button || node instanceof Text )
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

    public static Button createCheckAtomsButton(Pane pane)
    {
        Button checkAtomsButton = new Button("Submit");
        checkAtomsButton.getStyleClass().add("check-atoms-button");
        checkAtomsButton.setOnAction(event -> {
            Hexagon.checkForAtomAndChangeColor();
            makeGameUnplayable(pane);
            for (Node node : pane.getChildren()) {
                if (node instanceof Circle && ((Circle) node).getRadius() == ATOM_SIZE || node instanceof Circle) {
                    node.setVisible(true);
                }
            }
        });
        return checkAtomsButton;
    }

    public static Button displayRays(Pane pane)
    {
        Button checkRay = new Button("Show Rays");
        checkRay.getStyleClass().add("check-atoms-button");
        checkRay.setUserData("Show Rays");
        checkRay.setOnAction(event -> {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Line) {
                        node.setVisible(true); // make the line visible
                    }
                }
        });

        return checkRay;
    }

    public static void makeGameUnplayable(Pane pane) {
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node.getUserData() == null || !"Show Rays".equals(node.getUserData())) {
                node.setDisable(true);
            } else {
                node.setDisable(false);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
