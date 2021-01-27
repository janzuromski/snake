import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SnakePane extends GridPane {

    Tile[] tiles;
    Snake snake;

    int rows, cols;

    public SnakePane(int cols, int rows, int moveInterval) {
        this.cols = cols;
        this.rows = rows;
        tiles = new Tile[cols * rows];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
            add(tiles[i], i % cols, i / cols);
        }
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        setMaxSize(cols * Tile.SIDE, rows * Tile.SIDE);
        snake = new Snake(this, moveInterval);
    }

}
