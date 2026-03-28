package model;

public class Board {

    private int width;
    private int height;
    private Cell[][] grid;

    // Constructor
    public Board(int width, int height) {
    }

    public Cell getCell(int x, int y) {
        return null;
    }


    public void setCell(int x, int y, Cell cell) {

    }

    public boolean isFree(int x, int y) {
        return false;
    }


    private boolean isInside(int x, int y) {
        return false;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}