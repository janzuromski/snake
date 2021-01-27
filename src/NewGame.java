import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class NewGame extends Scene {

    GameBoard.Difficulty difficulty;
    int sizeNchoice, sizeMchoice;
    Stage stage;
    Engine engine;

    HBox sizes;
    VBox difficulties;
    Label sizeLabel;
    ComboBox<Integer> sizeN, sizeM;
    DifficultyButton low, medium, high;
    Integer[] nums;


    public NewGame(Stage stage, Engine engine) {
        super(new VBox(), 200, 200);
        this.stage = stage;
        this.engine = engine;
        sizeLabel = new Label("Game board dimensions:");
        nums = new Integer[21];
        for (int i = 0; i < nums.length; i++)
            nums[i] = i + 10;
        sizeN = new ComboBox<>(FXCollections.observableArrayList(nums));
        sizeM = new ComboBox<>(FXCollections.observableArrayList(nums));
        sizes = new HBox(10, sizeN, sizeM);
        sizes.setAlignment(Pos.CENTER);
        low = new DifficultyButton("LOW", GameBoard.Difficulty.LOW);
        medium = new DifficultyButton("MEDIUM", GameBoard.Difficulty.MEDIUM);
        high = new DifficultyButton("HIGH", GameBoard.Difficulty.HIGH);
        difficulties = new VBox(10, low, medium, high);
        difficulties.setAlignment(Pos.CENTER);
        ((VBox)rootProperty().get()).getChildren().addAll(sizeLabel, sizes, difficulties);
        ((VBox)rootProperty().get()).setSpacing(15);
        ((VBox)rootProperty().get()).setAlignment(Pos.CENTER);

    }

    class DifficultyButton extends Button {

        Alert alert;

        public DifficultyButton(String s, GameBoard.Difficulty choice) {
            super(s);
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unset properties");
            alert.setContentText("First, choose board dimensions from drop boxes placed above.");

            setOnAction(actionEvent -> {
                if (sizeN.getSelectionModel().isEmpty() | sizeM.getSelectionModel().isEmpty()) {
                    alert.showAndWait();
                } else {
                    sizeNchoice = sizeN.getSelectionModel().getSelectedItem();
                    sizeMchoice = sizeM.getSelectionModel().getSelectedItem();
                    difficulty = choice;
                    stage.setScene(new GameBoard(new VBox(), difficulty, sizeNchoice, sizeMchoice, engine, stage));
                }
            });
        }
    }
}
