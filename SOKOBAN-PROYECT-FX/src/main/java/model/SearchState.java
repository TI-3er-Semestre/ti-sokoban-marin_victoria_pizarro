package model;

import structures.LinkedList;
import structures.Queue;

public class SearchState {

    private Position playerPosition;
    private LinkedList<Box> boxes;
    private Queue<String> path;

    public SearchState(Position playerPosition,LinkedList<Box> boxes,Queue<String> path) {

        this.playerPosition = playerPosition;
        this.boxes = boxes;
        this.path = path;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public LinkedList<Box> getBoxes() {
        return boxes;
    }

    public Queue<String> getPath() {
        return path;
    }
}

