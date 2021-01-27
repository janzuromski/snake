import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Timer extends Label {
    public Timer() {
        setFont(Font.font(20));
    }

    public void updateText(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        setText(minutes + ":" + (seconds < 10 ? "0" + seconds : seconds));
    }
}
