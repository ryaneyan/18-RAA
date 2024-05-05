// CODE TO IMPLEMENT A MULTIPLAYER VERSION OF BLACKBOX+
// UNUSED

package app;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class AddPlayerPane {
    private AddPlayerPane(){}

    public static void addPlayer(Stage stage) {
        Label playerLabel = new Label("Enter name:");
        playerLabel.setFont(Font.font("Lucida Console", FontWeight.BOLD, 30));

        TextField playersInput = new TextField();
        playersInput.setStyle("-fx-background-color: grey; -fx-text-fill: yellow");
        playersInput.setFont(new Font(20));

        Button addPlayerButton = new Button("Confirm");
        addPlayerButton.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
        addPlayerButton.setFont(Font.font("Lucida Console", FontWeight.BOLD, 20));
        addPlayerButton.setTextAlignment(TextAlignment.LEFT);

        addPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (playersInput.getText().trim().isEmpty()) {
                    playersInput.setText("Invalid name!");
                } else {
                    Player.getPlayers().add(new Player(playersInput.getText()));
                    Player.incrementPlayerCount();
                    playersInput.clear();
                }
            }
        });
        
        VBox vbox = new VBox(playerLabel, playersInput, addPlayerButton);
        vbox.setSpacing(5);

        Popup popup = new Popup();
        popup.setX(500);
        popup.setY(300);
        popup.getContent().add(vbox);
        popup.show(stage);
    }
}
