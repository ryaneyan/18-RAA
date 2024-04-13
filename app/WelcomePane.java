package app;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class WelcomePane {

    private WelcomePane() {
    }

    public static Pane generateWelcomePane() {
        Pane welcomePane = new Pane();

        ImageView gifView = new ImageView();
        Image gif = new Image("file:app/assets/test.PNG");
        gifView.setImage(gif);

        gifView.setLayoutX(50);
        gifView.setLayoutY(200);
        gifView.setFitWidth(600);
        gifView.setFitHeight(600);

        RotateTransition rt = new RotateTransition(Duration.millis(60100), gifView);
        rt.setByAngle(360);
        rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.setAutoReverse(false);
        rt.play();
        welcomePane.getChildren().add(gifView);

        welcomePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Text welcomeText = new Text("Welcome to\nBlackBox+\n");
        welcomeText.setFill(Color.YELLOW);
        welcomeText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 40));
        welcomeText.setLayoutX(100);
        welcomeText.setLayoutY(150);
        welcomePane.getChildren().add(welcomeText);


        Text atomText = new Text("Atoms Amount:");
        atomText.setFill(Color.YELLOW);
        atomText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 30));
        atomText.setLayoutX(950);
        atomText.setLayoutY(350);
        welcomePane.getChildren().add(atomText);

        Label atomLabel = new Label(String.valueOf((int) Constants.ATOMS_AMOUNT));
        atomLabel.setFont(Font.font("Lucida Console", FontWeight.BOLD, 10));
        atomLabel.setTextFill(Color.YELLOW);

        Button decrementButton = new Button();
        decrementButton.setOnAction(e -> {
            Constants.ATOMS_AMOUNT = Math.max(Constants.ATOMS_AMOUNT - 1, 2); // Lower limit is 2
            atomLabel.setText(String.valueOf((int) Constants.ATOMS_AMOUNT));
        });

        Button incrementButton = new Button();
        incrementButton.setOnAction(e -> {
            Constants.ATOMS_AMOUNT = Math.min(Constants.ATOMS_AMOUNT + 1, 8); // Upper limit is 8
            atomLabel.setText(String.valueOf((int) Constants.ATOMS_AMOUNT));
        });

        HBox hbox = new HBox(decrementButton, atomLabel, incrementButton);
        hbox.setSpacing(30); // spacing between elements
        hbox.setLayoutX(950);
        hbox.setLayoutY(350);
        incrementButton.getStyleClass().add("increment-button");
        decrementButton.getStyleClass().add("decrement-button");

        welcomePane.getChildren().add(hbox);


        return welcomePane;
    }
}
