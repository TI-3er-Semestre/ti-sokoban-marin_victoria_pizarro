package model;

public class Cell {

    private CellType type;
    private boolean hasBox;

    // Constructor
    public Cell(CellType type, boolean hasBox) {
        this.type = type;
        this.hasBox = hasBox;
    }

    /**
     * Indica si la celda es caminable.
     * @return true si es FLOOR o GOAL
     */
    public boolean isWalkable() {
        return type == CellType.FLOOR || type == CellType.GOAL;
    }

    /**
     * Indica si la celda es un objetivo.
     * @return true si es tipo GOAL
     */
    public boolean isGoal() {

        return type == CellType.GOAL;
    }


    public CellType getType() {

        return type;
    }

    public void setType(CellType type) {

        this.type = type;
    }
    /**
     * Indica si la celda contiene una caja.
     * @return true si hay una caja
     */
    public boolean hasBox() {

        return hasBox;
    }

    public void setHasBox(boolean hasBox) {

            this.hasBox = hasBox;
    }
}