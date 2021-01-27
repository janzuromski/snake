import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Tile extends Rectangle {

    boolean isSnake;
    boolean isPlusPoint;
    boolean isMinusPoint;
    static int SIDE = 20;
    Transition transition;

    public Tile() {
        isSnake = false;
        isPlusPoint = false;
        isMinusPoint = false;
        setFill(Color.BLACK);
        setHeight(SIDE);
        setWidth(SIDE);

    }

    public void setSnake() {
        isSnake = true;
        isPlusPoint = false;
        isMinusPoint = false;
        setFill(Color.WHITE);
    }

    public void setPlusPoint() {
        isPlusPoint = true;
        isMinusPoint = false;
        isSnake = false;
        setFill(Color.YELLOW);
    }

    public void setMinusPoint() {
        isMinusPoint = true;
        isSnake = false;
        isPlusPoint = false;
        setFill(Color.RED);
    }

    public void setDefault() {
        isPlusPoint = false;
        isMinusPoint = false;
        isSnake = false;
        setFill(Color.BLACK);
    }

    public void setBounce() {
        if (isMinusPoint | isPlusPoint) {
            transition = new ScaleTransition(new Duration(500), this);
            ((ScaleTransition)transition).setByX(0.15);
            ((ScaleTransition)transition).setByY(0.15);
            transition.setAutoReverse(true);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.play();
        }
    }

    public void removeBounce() {
        assert transition != null;
        transition.jumpTo(new Duration(0));
        transition.stop();
    }
}
