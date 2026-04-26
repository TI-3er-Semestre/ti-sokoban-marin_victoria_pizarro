package model;
import structures.LinkedList;

public class GameState {
    private Position playerPosition;
    private LinkedList<Box> boxes;

    /**
     * construye un nuevo estado del juego
     * @param playerPosition la posicion actual del jugador
     * @param boxes la lista enlazada con todas las cajas del tablero
     */

    public GameState(Position playerPosition, LinkedList<Box> boxes){
        this.playerPosition = playerPosition;
        this.boxes = boxes;
    }
    // retorna la posicion actual del jugador


    public Position getPlayerPosition() {
        return playerPosition;
    }
    //retorna la lista de cajas del estado

    public LinkedList<Box> getBoxes() {
        return boxes;
    }


}
