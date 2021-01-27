import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class MainMenu extends Scene {

    Stage stage;
    Button newGame, highScores, exit;
    Label snakeIcon = new Label();
    Image snakeImage;
    ImageView snakeView;


    public MainMenu(Stage stage, Engine engine) {
        super(new VBox(15), 500, 350);
        this.stage = stage;
        prepareImage();
        newGame = new Button("Start");
        highScores = new Button("High Scores");
        exit = new Button("Exit");
        newGame.setOnAction(actionEvent -> stage.setScene(new NewGame(stage, engine)));
        highScores.setOnAction(actionEvent -> {
            HighScoresScene hss = new HighScoresScene(stage, this);
            stage.setScene(hss);
        });
        exit.setOnAction(actionEvent -> stage.close());
        HBox buttons = new HBox(15, newGame, highScores, exit);
        buttons.setAlignment(Pos.CENTER);
        rootProperty().get().setStyle("-fx-background-color: black;");
        ((VBox)rootProperty().get()).setAlignment(Pos.CENTER);
        ((VBox)rootProperty().get()).getChildren().addAll(snakeIcon, buttons);
    }

    private void prepareImage() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/images/snake1.png");
            snakeImage = new Image(fis);
        } catch (IOException ex) { ex.printStackTrace(); }
        snakeView = new ImageView(snakeImage);
        snakeIcon = new Label();
        snakeIcon.setGraphic(snakeView);
    }
}
