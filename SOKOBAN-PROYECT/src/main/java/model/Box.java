package model;

public class Box {

    private Position position;

    // Constructor
    public Box(Position position) {
        this.position = position;
    }

    // Mover caja
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

    // Getters y setters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
