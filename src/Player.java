import java.io.Serializable;

public class Player implements Serializable, Comparable<Player> {
    final String name;
    final int points;
    final int time;
    final GameBoard.Difficulty difficulty;
    int rankingScore;

    public Player(String name, int points, int time, GameBoard.Difficulty difficulty) {
        this.name = name;
        this.points = points;
        this.time = time;
        this.difficulty = difficulty;
        calculateRankingScore();
    }

    @Override
    public String toString() {
        return name + " " + difficulty + " points: " + points + " time: " + time / 60 + ":" + time % 60 +
                " Ranking Score: " + rankingScore;
    }

    @Override
    public int compareTo(Player o) {
        return rankingScore == o.rankingScore ? time - o.time : rankingScore - o.rankingScore;
    }

    private void calculateRankingScore() {
        switch (difficulty) {
            case HIGH: rankingScore = points * 5; break;
            case MEDIUM: rankingScore = points * 2; break;
            case LOW: rankingScore = points; break;
        }
    }
}
