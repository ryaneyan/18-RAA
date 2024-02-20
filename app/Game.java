package app;

import static app.Constants.*;
import javafx.application.Application;
import javafx.scene.Scene;
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

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("BlackBox+");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
