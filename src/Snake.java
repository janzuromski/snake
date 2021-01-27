import java.util.*;

class Snake {

    enum Directions { UP, DOWN, RIGHT, LEFT }

    private final SnakePane snakePane;
    Queue<Tile> body;
    int points, currentIndex;
    int moveInterval;

    public Snake(SnakePane snakePane, int moveInterval) {
        this.snakePane = snakePane;
        this.moveInterval = moveInterval;
        body = new LinkedList<>();
        points = 0;
        body.add(snakePane.tiles[snakePane.tiles.length / 2]);
        snakePane.tiles[snakePane.tiles.length / 2].setSnake();
        currentIndex = snakePane.tiles.length / 2;
        generatePoint();
    }

    public void generatePoint() {
        int position;
        do  position = (int) (Math.random() * snakePane.tiles.length);
        while (snakePane.tiles[position].isSnake);
        if (Math.random() < 0.9) snakePane.tiles[position].setPlusPoint();
        else snakePane.tiles[position].setMinusPoint();
        snakePane.tiles[position].setBounce();
    }

    public boolean moveSnake(Directions directions) {
        int nextIndex = 0;
        switch (directions) {
            case UP:
                nextIndex = currentIndex - snakePane.cols;
                if (nextIndex < 0) return false;
                break;
            case DOWN:
                nextIndex = currentIndex + snakePane.cols;
                if (nextIndex >= snakePane.tiles.length) return false;
                break;
            case LEFT:
                nextIndex = currentIndex - 1;
                if (nextIndex / snakePane.cols < currentIndex / snakePane.cols) return false;
                break;
            case RIGHT:
                nextIndex = currentIndex + 1;
                if (nextIndex / snakePane.cols > currentIndex / snakePane.cols) return false;
        }
        Tile nextTile = snakePane.tiles[nextIndex];
        Tile leftTile;
        body.add(nextTile);
        currentIndex = nextIndex;
        if (nextTile.isSnake) return false;
        if (nextTile.isPlusPoint) {
            generatePoint();
            nextTile.removeBounce();
            nextTile.setSnake();
            Pointer.add();
            return true;
        }
        if (nextTile.isMinusPoint) {
            if (body.size() > 2) {
                leftTile = body.poll();
                assert leftTile != null;
                leftTile.setDefault();
            }
            generatePoint();
            nextTile.removeBounce();
            nextTile.setSnake();
            Pointer.add();

        }
        nextTile.setSnake();
        leftTile = body.poll();
        assert leftTile != null;
        leftTile.setDefault();
        return true;
    }
}
