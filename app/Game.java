package app;

import static app.Constants.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    List<List<Hexagon>> hexBoard = new ArrayList<>();
    @Override
    public void start(Stage stage) {

        int limit = 5;
        boolean increasing = true;
        for (int i = 0; i < 9; i++) {
            hexBoard.add(new ArrayList<>());
            for (int j = 0; j < limit; j++) {
                if(increasing) hexBoard.get(i).add(new Hexagon(X_ORIGIN - (X_DIFF/2*i) + (X_DIFF*j), Y_ORIGIN + Y_DIFF*i, RADIUS));
                else hexBoard.get(i).add(new Hexagon(X_ORIGIN - (X_DIFF/2*(8-i)) + (X_DIFF*j), Y_ORIGIN + Y_DIFF*i, RADIUS));

            }
            if (i == 4) increasing = false;
            if (increasing) limit++;
            else limit--;
        }

        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        for (List<Hexagon> list: hexBoard) {
            root.getChildren().addAll(list);
        }
        Pane welcome = new Pane();
        Scene welcomeScene = new Scene(welcome);
        Scene main = new Scene(root);
        stage.setScene(welcomeScene);
        stage.setMaximized(true);
        welcome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(main);
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
