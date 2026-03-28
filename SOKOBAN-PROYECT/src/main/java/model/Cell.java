package model;

public class Cell {

    private CellType type;
    private boolean hasBox;

    // Constructor
    public Cell(CellType type, boolean hasBox) {
        this.type = type;
        this.hasBox = hasBox;
    }


    public boolean isWalkable() {
        return type != CellType.WALL;
    }


    public boolean isGoal() {
        return type == CellType.GOAL;
    }


    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean hasBox() {
        return hasBox;
    }

    public void setHasBox(boolean hasBox) {
        this.hasBox = hasBox;
    }
}
