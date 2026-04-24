package model;

public class GameResult implements Comparable<GameResult> {

    private String playerName;
    private int levelId;
    private int moves;
    private int pushes;
    private long timeSeconds;

    public GameResult(String playerName, int levelId, int moves, int pushes, long timeSeconds) {
        this.playerName = playerName;
        this.levelId = levelId;
        this.moves = moves;
        this.pushes = pushes;
        this.timeSeconds = timeSeconds;
    }

    /**
     * Comparación por movimientos (menos movimientos = mejor resultado).
     * Usado por la LinkedList para inserción ordenada.
     */
    @Override
    public int compareTo(GameResult other) {
        return Integer.compare(this.moves, other.moves);
    }

    public String getPlayerName()        { return playerName; }
    public int getLevelId()              { return levelId; }
    public int getMoves()                { return moves; }
    public int getPushes()               { return pushes; }
    public long getTimeSeconds()         { return timeSeconds; }

    @Override
    public String toString() {
        return playerName + " | Level: " + levelId + " | Moves: " + moves
                + " | Pushes: " + pushes + " | Time: " + timeSeconds + "s";
    }
}