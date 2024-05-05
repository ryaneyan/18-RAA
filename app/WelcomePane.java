package app;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;

public class WelcomePane {

    private WelcomePane() {
    }

    /**
     * Static method that configures the starting screen the user sees
     * when they open the game.
     *
     * @return Returns a Pane loaded with the PNGs, GIFs and buttons necessary
     * to play the game
     */

    public static Pane generateWelcomePane() {
        Pane welcomePane = new Pane();

        ImageView gifView = new ImageView();
        Image gif = new Image(WelcomePane.class.getResourceAsStream("/assets/test.PNG"));
        gifView.setImage(gif);

        gifView.layoutXProperty().bind(welcomePane.widthProperty().subtract(gifView.fitWidthProperty()).divide(2));
        gifView.setLayoutY(150);
        gifView.setFitWidth(600);
        gifView.setFitHeight(600);

        RotateTransition rt = new RotateTransition(Duration.millis(60100), gifView);
        rt.setByAngle(360);
        rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.setAutoReverse(false);
        rt.play();
        welcomePane.getChildren().add(gifView);

        ImageView top_left = new ImageView();
        Image toplef = new Image(WelcomePane.class.getResourceAsStream("/assets/des_toplef.png"));
        top_left.setImage(toplef);
        top_left.setLayoutY(20);
        welcomePane.getChildren().add(top_left);

        top_left.setFitWidth(175);
        top_left.setFitHeight(350);

        ImageView bot_left = new ImageView();
        Image botlef = new Image(WelcomePane.class.getResourceAsStream("/assets/des_botlef.png"));
        bot_left.setImage(botlef);
        bot_left.setLayoutY(500);
        bot_left.setLayoutX(15);
        welcomePane.getChildren().add(bot_left);

        bot_left.setFitWidth(175);
        bot_left.setFitHeight(350);


        welcomePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Text welcomeText = new Text("BlackBox+\n");
        welcomeText.setFill(Color.YELLOW);
        welcomeText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 40));
        welcomeText.layoutXProperty().bind(welcomePane.widthProperty().subtract(welcomeText.getBoundsInLocal().getWidth()).divide(2));

        welcomeText.setLayoutY(150);
        welcomePane.getChildren().add(welcomeText);


        Text atomText = new Text("Atoms Amount:");
        atomText.setFill(Color.YELLOW);
        atomText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 30));
        atomText.setLayoutX(1150);
        atomText.setLayoutY(350);

        welcomePane.getChildren().add(atomText);

        Label atomLabel = new Label(String.valueOf((int) Constants.ATOMS_AMOUNT));
        atomLabel.setFont(Font.font("Lucida Console", 5));
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
        hbox.setLayoutX(1150);
        hbox.setLayoutY(350);
        incrementButton.getStyleClass().add("increment-button");
        decrementButton.getStyleClass().add("decrement-button");

        welcomePane.getChildren().add(hbox);


        return welcomePane;
    }
}
