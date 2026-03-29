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


    public double calculateScore() {
        return 0;
    }


    public void reset() {

    }


    public void addMove() {

        ;
    }

    public void addPush() {


    }

    public int getMoves() {
        return moves;
    }
    public int getPushes() {
        return pushes;
    }
}