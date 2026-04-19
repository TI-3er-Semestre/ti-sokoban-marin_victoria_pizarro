package model;

public class Level {

    private int id;
    private Board board;


    public Level(int id, Board board) {
        this.id = id;
        this.board = board;
    }

    /**
     * Carga un nivel desde una representación compacta.
     * @param data cadena con los datos del nivel
     */
    public void loadFromCompact(String data) {

        //pendiente
    }

    /**
     * Verifica si el nivel está completado.
     * @return true si todas las cajas están en GOAL
     */

    public boolean isCompleted() {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);

                if (cell != null && cell.hasBox() && !cell.isGoal()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getId() {

        return id;
    }

    public Board getBoard() {

        return board;
    }
}