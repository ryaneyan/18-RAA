package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static app.Constants.*;
import static app.WelcomePane.generateWelcomePane;

public class Game extends Application {

    @Override
    public void start(Stage stage) {
        Pane welcomePane = generateWelcomePane();
        Scene main = new Scene(welcomePane);
        main.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Button startButton = new Button("Start");
        startButton.getStyleClass().add("Start");
        startButton.setPrefSize(200, 200);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                HexBoard.generateBoard();
                RootPane.generateRootPane();
                Pane rootPane = RootPane.getRootPane();

                for (Node hex : rootPane.getChildren()) {
                    if (hex instanceof Hexagon) {
                        Hexagon current = (Hexagon) hex;
                        current.setAreaOfInfluenceCount();
                    }
                }
                HexagonButton.createButtons(rootPane);

                Button displayRayButton = displayRays(rootPane);
                rootPane.getChildren().add(displayRayButton);
                displayRayButton.setLayoutX(1150);
                displayRayButton.setLayoutY(420);
                displayRayButton.setDisable(true);
                displayRayButton.getStyleClass().add("button-disable");

                Button checkAtomsButton = createCheckAtomsButton(rootPane, displayRayButton);
                rootPane.getChildren().add(checkAtomsButton);
                checkAtomsButton.setLayoutX(1150);
                checkAtomsButton.setLayoutY(320);

                ImageView key = new ImageView();
                Image key_rays = new Image(Game.class.getResourceAsStream("/assets/key_rays.png"));
                key.setImage(key_rays);
                key.setFitWidth(150);
                key.setFitHeight(150);
                key.setLayoutX(5);
                key.setLayoutY(630);
                rootPane.getChildren().add(key);
                main.setRoot(rootPane);
            }
        });
        welcomePane.getChildren().add(startButton);

        startButton.layoutXProperty().bind(welcomePane.widthProperty().subtract(startButton.widthProperty()).divide(2));
        startButton.setLayoutY(600);

        stage.setScene(main);
        stage.setFullScreen(true);
        stage.setTitle("BlackBox+");
        stage.show();
    }

    /**
     * Creates and confugiures a button to Submit current ray placements and calculates score
     * Activates button to reveal rays
     * @param pane Pane to which the button is added to
     * @param displayRay Button which makes rays visible
     * @return Button to submit ray guesses
     */
    public static Button createCheckAtomsButton(Pane pane, Button displayRay) {
        Button checkAtomsButton = new Button("Submit");
        checkAtomsButton.getStyleClass().add("check-atoms-button");
        checkAtomsButton.setOnAction(event -> {
            int incorrectGuesses = ATOMS_AMOUNT - Hexagon.checkForAtomAndChangeColor();
            System.out.println(incorrectGuesses);

            int atom_score = HexagonButton.getScore();
            atom_score += incorrectGuesses * 5;
            HexagonButton.setScore(atom_score);
            HexagonButton.updateScoreDisplay();

            makeGameUnplayable(pane);
            for (Node node : pane.getChildren()) {
                if (node instanceof Circle && ((Circle) node).getRadius() == ATOM_SIZE || node instanceof Circle) {
                    node.setVisible(true);
                }
            }

            ImageView key = new ImageView();
            Image key_atom = new Image(Game.class.getResourceAsStream("/assets/key_atoms.png"));
            key.setImage(key_atom);
            key.setFitWidth(200);
            key.setFitHeight(200);
            key.setLayoutX(0);
            key.setLayoutY(50);
            pane.getChildren().add(key);

            displayRay.setDisable(false);
            displayRay.getStyleClass().removeAll("button-disable");
        });
        return checkAtomsButton;
    }

    /**
     * Creates button which makes every ray fired to be visible
     * @param pane Pane to which button is added
     * @return Button which reveals rays
     */
    public static Button displayRays(Pane pane) {
        Button checkRay = new Button("Show Rays");
        checkRay.getStyleClass().add("check-atoms-button");
        checkRay.setUserData("Show Rays");
        checkRay.setOnAction(event -> {
            for (Node node : pane.getChildren()) {
                if (node instanceof Line) {
                    node.setVisible(!node.isVisible());
                }
            }
        });

        return checkRay;
    }

    /**
     * TODO
     * @param pane
     */
    public static void makeGameUnplayable(Pane pane) {
        for (Node node : pane.getChildren()) {
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
