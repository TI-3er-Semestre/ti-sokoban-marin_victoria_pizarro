package model;

public class Box implements Comparable<Box> {

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

    @Override

    public int compareTo(Box other){

        int comparacionX = Integer.compare(this.position.getX(),other.position.getX());
        if(comparacionX != 0){
            return comparacionX;
        }
        return Integer.compare(this.position.getY(),other.position.getY());
    }

    // Getters y setters
    public Position getPosition() {

        return position;
    }

    public void setPosition(Position position) {

        this.position = position;
    }
}