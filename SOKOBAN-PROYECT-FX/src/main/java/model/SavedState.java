package model;

import java.io.Serializable;

public class SavedState implements Serializable {

    private static final long serialVersionUID = 1L;

    private int levelId;
    private int playerX;
    private int playerY;
    private int moves;
    private int pushes;
    private int time;
    private String playerName;
    private int[] boxX;
    private int[] boxY;

    public SavedState(int levelId, int playerX, int playerY,
                      int moves, int pushes, int time, String playerName,
                      int[] boxX, int[] boxY) {

        this.levelId    = levelId;
        this.playerX    = playerX;
        this.playerY    = playerY;
        this.moves      = moves;
        this.pushes     = pushes;
        this.time       = time;
        this.playerName = playerName;
        this.boxX       = boxX;
        this.boxY       = boxY;
    }

    public int getLevelId()       { return levelId; }
    public int getPlayerX()       { return playerX; }
    public int getPlayerY()       { return playerY; }
    public int getMoves()         { return moves; }
    public int getPushes()        { return pushes; }
    public int getTime()          { return time; }
    public String getPlayerName() { return playerName; }
    public int[] getBoxX()        { return boxX; }
    public int[] getBoxY()        { return boxY; }
}