package model;
import structures.LinkedList;
import structures.CustomIterator;

public class GameState {
    private Position playerPosition;
    private LinkedList<Box> boxes;

    // Relaciones para BFS/DFS
    private GameState lastState;
    private String moveGenerated;
    private boolean pushedBox;

    /**
     * Construye un estado del juego (usado por Game para el historial de undo).
     */
    public GameState(Position playerPosition, LinkedList<Box> boxes, boolean pushedBox) {
        this.playerPosition = playerPosition;
        this.boxes = boxes;
        this.pushedBox = pushedBox;
    }

    /**
     * Construye un estado del juego para el grafo de BFS/DFS.
     * @param playerPosition posición del jugador en este estado
     * @param boxes          copia de las cajas en este estado
     * @param lastState      estado padre (null si es raíz)
     * @param moveGenerated  movimiento que generó este estado (null si es raíz)
     */
    public GameState(Position playerPosition, LinkedList<Box> boxes,
                     GameState lastState, String moveGenerated) {
        this.playerPosition = playerPosition;
        this.boxes = boxes;
        this.lastState = lastState;
        this.moveGenerated = moveGenerated;
    }

    /**
     * Genera una clave única para este estado.
     *
     * FIX: las posiciones de las cajas se ordenan antes de construir la clave,
     * para que dos estados con las mismas cajas en distinto orden en la lista
     * produzcan la misma clave y el BFS/DFS no los trate como estados distintos.
     *
     * Sin este fix el solver exploraba estados duplicados, causando ciclos y
     * retornando null aunque existiera solución.
     */
    public String generateKey() {
        // 1. Recolectar posiciones de cajas
        int count = boxes.getLength();
        int[] xs = new int[count];
        int[] ys = new int[count];

        CustomIterator<Box> it = boxes.iterator();
        int idx = 0;
        while (it.hasNext()) {
            Box box = it.next().getData();
            xs[idx] = box.getPosition().getX();
            ys[idx] = box.getPosition().getY();
            idx++;
        }

        // 2. Ordenar por (x, y) con burbuja — lista pequeña, O(n^2) es aceptable
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                boolean swap = (xs[j] > xs[j + 1]) ||
                        (xs[j] == xs[j + 1] && ys[j] > ys[j + 1]);
                if (swap) {
                    int tmpX = xs[j]; xs[j] = xs[j + 1]; xs[j + 1] = tmpX;
                    int tmpY = ys[j]; ys[j] = ys[j + 1]; ys[j + 1] = tmpY;
                }
            }
        }

        // 3. Construir la clave con cajas ordenadas
        StringBuilder key = new StringBuilder();
        key.append("p")
                .append(playerPosition.getX()).append(",")
                .append(playerPosition.getY()).append("|");

        for (int i = 0; i < count; i++) {
            key.append("b").append(xs[i]).append(",").append(ys[i]).append("|");
        }

        return key.toString();
    }

    // Getters
    public Position getPlayerPosition() { return playerPosition; }
    public LinkedList<Box> getBoxes()   { return boxes; }
    public GameState getLastState()     { return lastState; }
    public String getMoveGenerated()    { return moveGenerated; }
    public boolean isPushedBox()        { return pushedBox; }
}