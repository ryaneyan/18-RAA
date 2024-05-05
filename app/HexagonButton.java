package app;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.List;

import static app.Constants.*;

public class HexagonButton {
    public static Pane root;
    private static int score = 0;
    private static Label scoreLabel = new Label();
    static int counter = 1;

    public static void updateScoreDisplay() {
        scoreLabel.setText("Score " + getScore());
    }

    private static void setButtonActionAndAddToRoot(Pane root, Button button, int startX, int startY, int endX, int endY, RayDirection direction) {
        button.setOnAction(event -> {
            Ray ray = new Ray(startX, startY, direction);
            ray.displayRay(root);
            button.setDisable(true);

            int hitResult = ray.getFinalHitState();
            button.getStyleClass().removeAll("button-hit", "button-direct-hit", "button-other");

            double buttonLayoutX = button.getLayoutX();
            double buttonLayoutY = button.getLayoutY();

            if (hitResult == 2) {
                button.getStyleClass().add("button-direct-hit");
                score += 1;
            } else if (hitResult == 3) {
                button.getStyleClass().add("button-reflect-hit");
                score += 1;
            } else {
                button.getStyleClass().add("button-hit");
                score += 2;

                Label label1 = new Label(String.valueOf(HexagonButton.counter));
                label1.getStyleClass().add("ray-label");

                label1.setLayoutX(buttonLayoutX);
                label1.setLayoutY(buttonLayoutY);
                root.getChildren().add(label1);
                counter++;
            }

            scoreLabel.getStyleClass().add("vbox-text");
            VBox vbox = new VBox(scoreLabel);
            vbox.getStyleClass().add("container");
            vbox.setLayoutX(1165);
            vbox.setLayoutY(200);
            root.getChildren().add(vbox);
            updateScoreDisplay();

        });

        root.getChildren().add(button);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int atom_score) {
        score = atom_score;
    }

    public static void drawButton(Pane root, int startX, int startY, int endX, int endY, int Flag) {
        double centerX = HexBoard.getHexBoard().get(startX).get(startY).getCentreX();
        double centerY = HexBoard.getHexBoard().get(startX).get(startY).getCentreY() - 10;

        RayDirection direction = null;

        if (Flag == 1) {
            if ((startY < 5 && startX == 0)) {
                centerX += HEX_OFFSET_X;
                centerY -= HEX_OFFSET_Y + 20;
                direction = RayDirection.DIAGONAL_DOWN_LEFT;
            }
            if ((startX == 1 || startX == 2 || startX == 3 || startX == 4) && startY == 0) {
                centerX -= HEX_RADIUS;
                centerY += HEX_OFFSET_Y - 10;
                direction = RayDirection.HORIZONTAL_RIGHT;
            }
            if ((startY == 0 && startX > 4) && !(startY == 0 && startX == 8)) {
                centerX -= RADIUS + 10;
                centerY += HEX_OFFSET_Y;
                direction = RayDirection.HORIZONTAL_RIGHT;
            }
            if (startX == 8 && startY < 5) {
                centerX += HEX_OFFSET_X;
                centerY += HEX_RADIUS;
                direction = RayDirection.DIAGONAL_UP_LEFT;
            }
            if (!(startX == 0 && startY == 4) && !(startX == 8 && startY == 4)) {
                if (startY == HexBoard.getHexBoard().get(startX).size() - 1 && startX > 4) {
                    centerX += HEX_RADIUS;
                    centerY += HEX_OFFSET_Y;
                    direction = RayDirection.HORIZONTAL_LEFT;
                }
                if (startY == HexBoard.getHexBoard().get(startX).size() - 1 && startX < 5) {
                    centerX += HEX_RADIUS;
                    centerY += HEX_OFFSET_Y - 10;
                    direction = RayDirection.HORIZONTAL_LEFT;
                }
            }
        } else if (Flag == 2) {
            if (startX == 0 && startY == 0) {
                centerX -= HEX_RADIUS;
                centerY += HEX_OFFSET_Y - 10;
                direction = RayDirection.HORIZONTAL_RIGHT;
            }
            if (startX == 0 && startY == 4) {
                centerX += HEX_RADIUS;
                centerY += HEX_OFFSET_Y - 10;
                direction = RayDirection.HORIZONTAL_LEFT;
            }
            if (startX == 4 && startY == 0) {
                centerX -= HEX_OFFSET_X;
                centerY += HEX_RADIUS;
                direction = RayDirection.DIAGONAL_UP_RIGHT;
            }
            if (startX == 4 && startY == 8) {
                centerX += HEX_OFFSET_X;
                centerY += HEX_RADIUS;
                direction = RayDirection.DIAGONAL_UP_LEFT;
            }
            if (startX == 8 && startY == 0) {
                centerX -= HEX_RADIUS;
                centerY += HEX_OFFSET_Y;
                direction = RayDirection.HORIZONTAL_RIGHT;
            }
            if (startX == 8 && startY == 4) {
                centerX += HEX_RADIUS;
                centerY += HEX_OFFSET_Y;
                direction = RayDirection.HORIZONTAL_LEFT;
            }
        } else {
            if (startY == 0 && startX < 5 || startX == 0) {
                centerX -= HEX_OFFSET_X;
                centerY -= HEX_OFFSET_Y + 20;
                direction = RayDirection.DIAGONAL_DOWN_RIGHT;
            }
            if (startY == 0 && startX > 4) {
                centerX -= HEX_OFFSET_X;
                centerY += HEX_RADIUS;
                direction = RayDirection.DIAGONAL_UP_RIGHT;
            }
            if (startX == 8 && startY > 0) {
                centerX -= HEX_OFFSET_X;
                centerY += HEX_RADIUS;
                direction = RayDirection.DIAGONAL_UP_RIGHT;
            }
            if (!(startX == 0 && startY == 4) && !(startX == 8 && startY == 4)) {
                if (startY == HexBoard.getHexBoard().get(startX).size() - 1 && startX > 4) {
                    centerX += HEX_OFFSET_X;
                    centerY += HEX_RADIUS;
                    direction = RayDirection.DIAGONAL_UP_LEFT;
                }
                if (startY == HexBoard.getHexBoard().get(startX).size() - 1 && startX < 5) {
                    centerX += HEX_OFFSET_X;
                    centerY -= HEX_OFFSET_Y + 20;
                    direction = RayDirection.DIAGONAL_DOWN_LEFT;
                }
            }
        }

        Button button = new Button();
        double buttonRadius = (RADIUS / 4) - 2;
        button.getStyleClass().add("custom-button");
        setDirectionStyle(button, direction);
        button.setLayoutX(centerX - buttonRadius);
        button.setLayoutY(centerY - buttonRadius);
        button.setMinSize(2 * buttonRadius, 2 * buttonRadius);
        button.setMaxSize(2 * buttonRadius, 2 * buttonRadius);

        setButtonActionAndAddToRoot(root, button, startX, startY, endX, endY, direction);
    }

    private static void setDirectionStyle(Button button, RayDirection direction) {
        switch (direction) {
            case DIAGONAL_DOWN_LEFT:
                button.getStyleClass().add("diagonal-down-left");
                break;
            case DIAGONAL_DOWN_RIGHT:
                button.getStyleClass().add("diagonal-down-right");
                break;
            case DIAGONAL_UP_LEFT:
                button.getStyleClass().add("diagonal-up-left");
                break;
            case DIAGONAL_UP_RIGHT:
                button.getStyleClass().add("diagonal-up-right");
                break;
            case HORIZONTAL_LEFT:
                button.getStyleClass().add("horizontal-left");
                break;

            default:
                break;
        }
    }

    public static void createButtons(Pane rootPane) {
        root = rootPane;
        List<List<Hexagon>> hexBoard = HexBoard.getHexBoard();

        for (int l = 0; l < 2; ++l) {
            for (int x = 0; x < hexBoard.size(); x++) {
                int Flag = 0;
                if (l == 1) {
                    Flag = 1;
                } 
                for (int y = 0; y < hexBoard.get(x).size(); y++) {
                    if (isOuterHexagon(x, y, hexBoard.size(), hexBoard.get(x).size())) {
                        int endX, endY;
                        if (x == 0 || y == hexBoard.get(x).size() - 1) {
                            endX = x + hexBoard.get(x).size() - 1 - y;
                            endY = hexBoard.get(x).size() - 1;
                        } else {
                            endX = x;
                            endY = hexBoard.get(x).size() - 1;
                        }
                        drawButton(root, x, y, endX, endY, Flag);
                    }
                }
            }
        }
        drawButton(root, 0, 0, 0, 0, 2);
        drawButton(root, 0, 4, 0, 0, 2);

        drawButton(root, 4, 0, 0, 0, 2);
        drawButton(root, 4, 8, 0, 0, 2);

        drawButton(root, 8, 0, 0, 0, 2);
        drawButton(root, 8, 4, 0, 0, 2);
    }

    private static boolean isOuterHexagon(int x, int y, int numRows, int numCols) {
        return x == 0 || y == 0 || x == numRows - 1 || y == numCols - 1;
    }

    public static Button getButtonAtPoint(Point2D point, Pane root) {
        for (Node node : root.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getBoundsInParent().contains(point)) {
                    return button;
                }
            }
        }
        return null;
    }
}

