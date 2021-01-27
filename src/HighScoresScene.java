import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScoresScene extends Scene {

    Button ok;
    HighScores highScores;
    ScrollPane scrollPane;
    Stage stage;
    Scene previousScene;

    public HighScoresScene(Stage stage, Scene previousScene) {
        super(new VBox(15), 400, 400);
        this.stage = stage;
        this.previousScene = previousScene;
        ok = new Button("Return");
        highScores = new HighScores();
        scrollPane = new ScrollPane(highScores);
        ok.setOnAction(actionEvent -> stage.setScene(previousScene));
        ((VBox)rootProperty().get()).setAlignment(Pos.CENTER);
        ((VBox)rootProperty().get()).getChildren().addAll(scrollPane, ok);
    }
}
