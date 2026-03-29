package model;

public class Box {

    private Position position;

    // Constructor
    public Box(Position position) {
        this.position = position;
    }

    // Mover caja
    public void move(String direction) {

    }

    // Getters y setters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}