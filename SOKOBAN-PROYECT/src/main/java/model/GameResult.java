package model;

public class GameResult implements Comparable<GameResult> {

    private User user;
    private int levelId;
    private int moves;
    private int pushes;
    private long timeSeconds;

    public GameResult(User user, int levelId, int moves, int pushes, long timeSeconds) {
        this.user = user;
        this.levelId = levelId;
        this.moves = moves;
        this.pushes = pushes;
        this.timeSeconds = timeSeconds;
    }

    /**
     * Comparación por movimientos (menos movimientos = mejor resultado).
     * Usado por la PriorityQueue para ordenar en el leaderboard.
     */
    @Override
    public int compareTo(GameResult other) {
        return Integer.compare(this.moves, other.moves);
    }

    // Si en algún lado se necesita solo el nombre, lo saca del User
    public String getPlayerName()    { return user.getUsername(); }
    public User getUser()            { return user; }
    public int getLevelId()          { return levelId; }
    public int getMoves()            { return moves; }
    public int getPushes()           { return pushes; }
    public long getTimeSeconds()     { return timeSeconds; }

    @Override
    public String toString() {
        return user.getUsername() + " | Level: " + levelId
                + " | Moves: " + moves + " | Pushes: " + pushes
                + " | Time: " + timeSeconds + "s";
    }
}