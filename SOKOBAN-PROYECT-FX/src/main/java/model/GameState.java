package model;
import structures.LinkedList;
import structures.CustomIterator;

public class GameState {
    private Position playerPosition;
    private LinkedList<Box> boxes;

    //Relaciones para BFS
    private GameState lastState;
    private String moveGenerated;

    /**
     * construye un nuevo estado del juego
     * @param playerPosition la posicion actual del jugador
     * @param boxes la lista enlazada con todas las cajas del tablero
     */

    public GameState(Position playerPosition, LinkedList<Box> boxes){
        this.playerPosition = playerPosition;
        this.boxes = boxes;
    }

    /**
     * Construye un estado del juego para el grafo de BFS.
     * @param playerPosition posición del jugador en este estado
     * @param boxes          copia de las cajas en este estado
     * @param lastState   estado padre (null si es raíz)
     * @param moveGenerated   movimiento que generó este estado (null si es raíz)
     */
    public GameState(Position playerPosition, LinkedList<Box> boxes,  GameState lastState, String moveGenerated) {
        this.playerPosition = playerPosition;
        this.boxes = boxes;
        this.lastState = lastState;
        this.moveGenerated = moveGenerated;
    }

    //Tiene que generar una llave en para que la hashTable
    // la reciba con la intención de no re-visitar/re-explorar estados

    public String generateKey() {
        StringBuilder key = new StringBuilder();

        //Convierte la posicion del jugador en String
        key.append("p").append(playerPosition.getX()).append(",").append(playerPosition.getY()).append("|");

        CustomIterator<Box> it = boxes.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            //Convierte la posicion de la caja en String
            key.append("b").append(box.getPosition().getX()).append(",").append(box.getPosition().getY()).append("|");
        }

        return key.toString();
    }


    //Getters --------------------
    // retorna la posicion actual del jugador
    public Position getPlayerPosition() {
        return playerPosition;
    }
    //retorna la lista de cajas del estado

    public LinkedList<Box> getBoxes() {
        return boxes;
    }
    public GameState       getLastState()    { return lastState;    }
    public String          getMoveGenerated()    { return moveGenerated; }


    }
