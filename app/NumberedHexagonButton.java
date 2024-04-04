package app;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.util.List;

import static app.Constants.*;

public class NumberedHexagonButton {

    public static void drawNumber(Pane root, double centerX, double centerY, int number) {
        Text text = new Text(Double.toString(number));
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial", 12)); // You can adjust the font and size as needed
        text.setX(centerX);
        text.setY(centerY);

        root.getChildren().add(text);
    }

    public static void createButtons(Pane root) {
        List<List<Hexagon>> hexBoard = HexBoard.getHexBoard();
        int number = 1;

        for (int x = 0; x < hexBoard.size(); x++) {
            for (int y = 0; y < hexBoard.get(x).size(); y++) {
                if (isOuterHexagon(x, y, hexBoard.size(), hexBoard.get(x).size())) {
                    double centerX = hexBoard.get(x).get(y).getCentreX();
                    double centerY = hexBoard.get(x).get(y).getCentreY() - 10;

                    drawNumber(root, centerX, centerY, number++);
                }
            }
        }
    }

    private static boolean isOuterHexagon(int x, int y, int numRows, int numCols) {
        return x == 0 || y == 0 || x == numRows - 1 || y == numCols - 1;
    }
}
