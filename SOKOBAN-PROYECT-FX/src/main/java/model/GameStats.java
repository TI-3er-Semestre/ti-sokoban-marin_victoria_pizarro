package model;

public class GameStats implements Comparable<GameStats> {

    private int moves;
    private int pushes;
    private int time;
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
     * Compara dos GameStats por su puntaje.
     * El orden está invertido para que el árbol binario entregue
     * el ranking de mayor a menor puntaje al hacer inorder().
     * @param other el otro GameStats a comparar
     * @return negativo si this tiene mayor score, positivo si tiene menor score
     */
    @Override
    public int compareTo(GameStats other) {

        return Double.compare(other.calculateScore(), this.calculateScore());
    }

    /**
     * Reinicia las estadísticas del juego.
     */
    public void reset() {
        moves = 0;
        pushes = 0;

    }
    public synchronized void resetTime() {
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
    public void removeMove() {
        if (moves > 0) { moves--; } }

    public int getMoves() {

        return moves;
    }
    public int getPushes() {

        return pushes;
    }
    public synchronized void addSecond() {

        time++;
    }



    public synchronized int getTime() {
        return time;
    }

    public  synchronized void  setTime(int time) {
        this.time = time;
    }

    public int getLevelId() {
        return levelId;
    }
    @Override
    public String toString() {
        return "GameStats { levelId=" + levelId
                + ", moves=" + moves
                + ", pushes=" + pushes
                + ", time=" + time
                + ", score=" + calculateScore() + " }";
    }
}