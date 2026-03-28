
package model;
public class Game {

    private Board board;
    private Player player;
    private Level currentLevel;
    private GameStats stats;
    private User user;
    private boolean isFinished;


    public Game(Level level, User user) {
        this.currentLevel = level;
        this.user = user;
        this.isFinished = false;
    }


    public void startGame() {

    }


    public void movePlayer(String direction) {

    }


    public void updateGame() {

    }


    public boolean checkWin() {

        return false;
    }


    public void resetGame() {

    }


    public void saveGame() {

    }

    public boolean isFinished() {
        return isFinished;
    }
}