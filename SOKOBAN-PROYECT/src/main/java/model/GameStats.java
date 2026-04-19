package model;

public class GameStats {

    private int moves;
    private int pushes;
    private double time;
    private int levelId;

    // Constructor
    public GameStats(int levelId) {
        this.levelId = levelId;
        this.moves = 0;
        this.pushes = 0;
        this.time = 0;
    }

    /**
     * Calcula el puntaje del juego basado en movimientos y empujes.
     * @return puntaje calculado
     */
    public double calculateScore() {
        double score = 1000 - (moves * 10 + pushes * 20);

        if (score < 0) {
            score = 0;
        }

        return score;
    }

    /**
     * Reinicia las estadísticas del juego.
     */
    public void reset() {
        moves = 0;
        pushes = 0;
        time = 0;
    }

    /**
     * Incrementa el contador de movimientos.
     */
    public void addMove() {
        moves++;
    }
    /**
     * Incrementa el contador de empujes.
     */
    public void addPush() {
        pushes++;

    }

    public int getMoves() {

        return moves;
    }
    public int getPushes() {

        return pushes;
    }
}