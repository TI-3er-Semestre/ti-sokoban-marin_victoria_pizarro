package model;

public class Box {

    private Position position;

    // Constructor
    public Box(Position position) {

        this.position = position;
    }

    /**
     * Mueve la caja en una dirección específica.
     * @param direction dirección del movimiento ("UP", "DOWN", "LEFT", "RIGHT")
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

    // Getters y setters
    public Position getPosition() {

        return position;
    }

    public void setPosition(Position position) {

        this.position = position;
    }
}