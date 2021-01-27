import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GameBoard extends Scene implements Runnable {

    enum Difficulty { LOW, MEDIUM, HIGH }

    Engine engine;
    Stage stage;
    SnakePane snakePane;
    DirectionButton
            down = new DirectionButton(Snake.Directions.DOWN, Snake.Directions.UP),
            up = new DirectionButton(Snake.Directions.UP, Snake.Directions.DOWN),
            right = new DirectionButton(Snake.Directions.RIGHT, Snake.Directions.LEFT),
            left = new DirectionButton(Snake.Directions.LEFT, Snake.Directions.RIGHT);
    Timer timer;
    Pointer pointer;
    HBox infoPanel;
    GridPane arrows;
    MenuBar menuBar;
    Menu options;
    MenuItem end;

    Difficulty difficulty;
    int sizeN, sizeM;
    boolean go = true, started = false;
    Snake.Directions currentDirection = Snake.Directions.RIGHT;
    int moveInterval = 100, counter = 0;
    Thread thread;

    public GameBoard(VBox root, Difficulty difficulty, int sizeN, int sizeM, Engine engine, Stage stage) {
        super(root, sizeM * Tile.SIDE + 40, sizeN * Tile.SIDE + 200);
        this.engine = engine;
        this.stage = stage;
        root.setStyle("-fx-background-color: darkgray;");
        setSpeed(difficulty);
        end = new MenuItem("end");
        end.setOnAction(actionEvent -> System.exit(0));
        end.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));
        options = new Menu("options");
        options.getItems().add(end);
        menuBar = new MenuBar(options);
        snakePane = new SnakePane(sizeM, sizeN, moveInterval);
        timer = new Timer();
        pointer = new Pointer();
        infoPanel = new HBox(20, timer, pointer);
        infoPanel.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        this.difficulty = difficulty;
        this.sizeN = sizeN;
        this.sizeM = sizeM;
        arrows = new GridPane();
        arrows.setAlignment(Pos.CENTER);
        arrows.add(down, 1, 2);
        arrows.add(up, 1, 0);
        arrows.add(right, 2, 1);
        arrows.add(left, 0, 1);
        root.getChildren().addAll(menuBar, infoPanel, snakePane, arrows);
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case S: case DOWN: down.fire(); break;
                case W: case UP: up.fire(); break;
                case D: case RIGHT: right.fire(); break;
                case A: case LEFT: left.fire(); break;
            }
        });
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    class DirectionButton extends Button {

        Snake.Directions direction;
        Snake.Directions oppositeDirection;

        public DirectionButton(Snake.Directions direction, Snake.Directions oppositeDirection) {
            this.direction = direction;
            this.oppositeDirection = oppositeDirection;
            setIcon();
            setOnAction(actionEvent -> {
                if (!started) { thread.start(); started = true; }
                if (currentDirection != oppositeDirection) currentDirection = direction;
            });
        }

        private void setIcon() {
            FileInputStream fis;
            try {
                switch (direction) {
                    case RIGHT: fis = new FileInputStream("src/images/arrow_right.png"); break;
                    case LEFT: fis = new FileInputStream("src/images/arrow_left.png"); break;
                    case DOWN: fis = new FileInputStream("src/images/arrow_down.png"); break;
                    default: fis = new FileInputStream("src/images/arrow_up.png");
                }
                setGraphic(new ImageView(new Image(fis)));
            } catch (IOException ex) { ex.printStackTrace(); }

        }

    }

    @Override
    public void run() {
        while (go) {
            Platform.runLater(() -> {
                pointer.update();
                timer.updateText(counter / 100);
            });
            go = snakePane.snake.moveSnake(currentDirection);
            try {
                TimeUnit.MILLISECONDS.sleep(moveInterval);
                counter += moveInterval / 10;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Platform.runLater(() -> {
            Dialog<String> dialog = new TextInputDialog();
            dialog.setHeaderText("The game has ended");
            dialog.setContentText("Please type your name to save your result in the Highscores Table");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> HighScores.addPlayer(s, Pointer.getPoints(), counter/ 100, difficulty));
            stage.close();
            engine.restart();
        });

    }

    private void setSpeed(Difficulty difficulty) {
        switch (difficulty) {
            case LOW: moveInterval = 300; break;
            case MEDIUM: moveInterval = 200; break;
            case HIGH: moveInterval = 100; break;
        }
    }
}
