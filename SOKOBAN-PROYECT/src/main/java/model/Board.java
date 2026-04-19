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
    /**
     * Obtiene la celda en una posición específica del tablero.
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @return la celda en la posición indicada o null si está fuera del tablero
     */
    public Cell getCell(int x, int y) {
        if (isInside(x, y)) {
            return grid[x][y];
        } else {
            return null;
        }
    }
    /**
     * Asigna una celda en una posición específica del tablero.
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @param cell celda a asignar
     */
    public void setCell(int x, int y, Cell cell) {
        if (isInside(x, y)) {
            grid[x][y] = cell;
        }
    }
    /**
     * Verifica si una celda está libre (caminable y sin caja).
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @return true si la celda es válida, caminable y sin caja
     */
    public boolean isFree(int x, int y) {
        boolean result = false;

        if (isInside(x, y)) {
            Cell cell = grid[x][y];

            if (cell != null) {
                result = cell.isWalkable() && !cell.hasBox();
            }
        }

        return result;
    }

    /**
     * Verifica si una posición está dentro de los límites del tablero.
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @return true si está dentro del tablero
     */
    private boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}