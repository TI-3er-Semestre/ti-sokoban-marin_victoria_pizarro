package model;

public class Player {

    private Position position;

    // Constructor
    public Player(Position position) {
        this.position = position;
    }

    // Mover jugador
    public void move(String direction) {
        int x = position.getX();
        int y = position.getY();

        switch (direction.toLowerCase()) {
            case "up":
                y--;
                break;
            case "down":
                y++;
                break;
            case "left":
                x--;
                break;
            case "right":
                x++;
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