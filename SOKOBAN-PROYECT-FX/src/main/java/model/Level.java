package model;
import structures.CustomIterator;

public class Level implements Comparable<Level> {

    private int id;
    private Board board;
    private Position playerStart;


    public Level(int id, Board board, Position playerStart) {
        this.id = id;
        this.board = board;
        this.playerStart = playerStart;
    }
    @Override
    public int compareTo(Level other) {
        return Integer.compare(this.id, other.id);
    }
    /**
     * Carga un nivel desde una representación compacta.
     * @param data cadena con los datos del nivel
     */
    public void loadFromCompact(String data) {

        //pendiente
    }
    /**
     * Verifica si el nivel está completado recorriendo la lista enlazada de cajas.
     * El nivel se completa cuando TODAS las cajas están sobre una celda GOAL.
     * @return true si todas las cajas están en posiciones objetivo
     */
    public boolean isCompleted() {

        CustomIterator<Box> iterator = board.getBoxes().iterator();
        while (iterator.hasNext()) {
            Box box = iterator.next().getData();
            int x = box.getPosition().getX();
            int y = box.getPosition().getY();
            Cell cell = board.getCell(x, y);

            if (cell == null || !cell.isGoal()) {
                return false;
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