package model;

public class Position {

    private int x;
    private int y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Compara si dos posiciones son iguales.
     * @param other posición a comparar
     * @return true si ambas posiciones coinciden
     */
    public boolean isSamePosition(Position other) {

        boolean equals = false;

        if (other != null) {
            if (this.x == other.x && this.y == other.y) {
                equals = true;
            }
        }

        return equals;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}