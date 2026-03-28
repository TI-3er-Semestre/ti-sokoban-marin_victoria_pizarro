package model;

public class Level {

    private int id;
    private Board board;


    public Level(int id, Board board) {
        this.id = id;
        this.board = board;
    }


    public void loadFromCompact(String data) {

    }


    public boolean isCompleted() {
        // TODO: lógica (ej: todas las cajas en GOAL)
        return false;
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
}