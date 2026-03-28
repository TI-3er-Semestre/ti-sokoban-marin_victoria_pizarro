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
        return false;
    }


    public boolean isGoal() {
        return false;
    }


    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean hasBox() {
        return false;
    }

    public void setHasBox(boolean hasBox) {
        this.hasBox = hasBox;
    }
}