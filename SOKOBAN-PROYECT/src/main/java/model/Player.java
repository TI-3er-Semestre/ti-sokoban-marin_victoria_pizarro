package model;

public class Player {

    private Position position;

    // Constructor
    public Player(Position position) {
        this.position = position;
    }

    // Mover jugador
    public void move(String direction) {

    }


    public Position getPosition() {
        return position;
    }


    public void setPosition(Position pos) {
        this.position = pos;
    }
}