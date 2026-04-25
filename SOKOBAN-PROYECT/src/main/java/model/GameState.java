package model;
import structures.LinkedList;

public class GameState {
    private Position playerPosition;
    private LinkedList<Box> boxes;

    public GameState(Position playerPosition, LinkedList<Box> boxes){
        this.playerPosition = playerPosition;
        this.boxes = boxes;
    }


    public Position getPlayerPosition() {
        return playerPosition;
    }

    public LinkedList<Box> getBoxes() {
        return boxes;
    }


}
