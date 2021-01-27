import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScores extends ListView<Player> {

    static List<Player> players;
    static final String FILE_PATH = "src/highscores";

    public HighScores() {
        super(FXCollections.observableList(loadPlayers()));

        setMinWidth(380);
    }

    public static List<Player> loadPlayers() {
        FileInputStream fis;
        ObjectInputStream ois;
        players = new ArrayList<>();
        try {
            fis = new FileInputStream(FILE_PATH);
            ois = new ObjectInputStream(fis);
            try {
                while (true) {
                    players.add((Player) ois.readObject());
                }
            } catch (EOFException ignored) {}
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            players = new ArrayList<>();
        }
        Collections.sort(players);
        Collections.reverse(players);
        return players;
    }

    public static void savePLayers() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        Collections.sort(players);
        Collections.reverse(players);
        try {
            fos = new FileOutputStream(FILE_PATH);
            oos = new ObjectOutputStream(fos);
            for (Player p : players)
                oos.writeObject(p);
            oos.close();
            fos.close();
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    public static void addPlayer(String name, int points, int time, GameBoard.Difficulty difficulty) {
        loadPlayers();
        players.add(new Player(name, points, time, difficulty));
        Collections.sort(players);
        Collections.reverse(players);
        savePLayers();
    }
}
