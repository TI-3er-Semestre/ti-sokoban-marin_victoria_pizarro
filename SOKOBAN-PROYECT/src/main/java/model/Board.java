package model;

public class Board {

    private int width;
    private int height;
    private Cell[][] grid;

    // Constructor
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
    }

    public Cell getCell(int x, int y) {
        if (isInside(x, y)) {
            return grid[x][y];
        }
        return null;
    }


    public void setCell(int x, int y, Cell cell) {
        if (isInside(x, y)) {
            grid[x][y] = cell;
        }
    }

    public boolean isFree(int x, int y) {
        Cell cell = getCell(x, y);
        return cell != null && cell.isWalkable() && !cell.hasBox();
    }


    private boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}