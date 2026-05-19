package model;

public class Player {

    private Position position;

    // Constructor
    public Player(Position position) {

        this.position = position;
    }

    /**
     * Mueve al jugador en una dirección.
     * @param direction dirección del movimiento
     */
    public void move(String direction) {
        int x = position.getX();
        int y = position.getY();

        switch (direction.toUpperCase()) {
            case "UP": y--;
                break;
            case "DOWN": y++;
                break;
            case "LEFT": x--;
                break;
            case "RIGHT": x++;
                break;
        }

        position.setX(x);
        position.setY(y);
    }


    public Position getPosition() {

        return position;
    }


    public void setPosition(Position pos) {

        this.position = pos;
    }
}