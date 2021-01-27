import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Pointer extends Label {

    static int points = 0;

    public Pointer() { setFont(Font.font(20)); }

    public void update() { setText("" + points); }

    public static void add() { points++; }

    public static int getPoints() { return points; }
}
