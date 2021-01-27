import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Engine extends Application {

    Scene scene;

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) {
        scene = new MainMenu(stage, this);
        stage.setScene(scene);
        stage.show();
    }

    public void restart() {
        start(new Stage());
    }

}
