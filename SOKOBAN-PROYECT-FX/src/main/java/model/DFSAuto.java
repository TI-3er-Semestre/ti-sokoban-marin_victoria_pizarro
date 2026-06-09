package model;

import structures.Stack;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;

public class DFSAuto {

    private Board board;
    private FloydWarshall fw;
    private Dijkstra dijkstra;
    private Position[] goalPositions;
    private int goalCount;

    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};
    private static final int MAX_DEPTH = 500;

    public DFSAuto(Board board) {
        this.board = board;
        this.fw = null;
        this.dijkstra = null;
        loadGoalPositions();
    }

    public DFSAuto(Board board, FloydWarshall fw) {
        this.board = board;
        this.fw = fw;
        this.dijkstra = null;
        loadGoalPositions();
    }

    public DFSAuto(Board board, Dijkstra dijkstra) {
        this.board = board;
        this.fw = null;
        this.dijkstra = dijkstra;
        loadGoalPositions();
    }

    /**
     * Resuelve el nivel desde el estado inicial usando DFS con limite de profundidad.
     * @param initialState estado de partida con posicion del jugador y cajas
     * @return lista de movimientos que resuelven el nivel, o null si no hay solucion
     */
    public LinkedList<String> solve(GameState initialState) {
        Stack<GameState> dfStack = new Stack<>();
        HashTable<String, Boolean> visited = new HashTable<>(1024);

        dfStack.push(initialState);
        visited.put(initialState.generateKey(), true);

        while (!dfStack.isEmpty()) {
            GameState current = dfStack.pop();

            if (getDepth(current) > MAX_DEPTH) continue;

            if (isGoalState(current)) {
                return reconstructPath(current);
            }

            for (int i = DIRECTIONS.length - 1; i >= 0; i--) {
                GameState next = applyMove(current, DIRECTIONS[i]);

                if (next != null) {
                    String key = next.generateKey();

                    if (!visited.containsKey(key)) {

                        if (fw != null) {
                            Position[] boxPositions = boxesToPositionArray(next.getBoxes());
                            int h = fw.computeHeuristic(boxPositions, boxPositions.length,
                                    goalPositions, goalCount);
                            if (h == FloydWarshall.INF) continue;
                        }

                        if (dijkstra != null) {
                            Position[] boxPositions = boxesToPositionArray(next.getBoxes());
                            int h = dijkstra.computeHeuristic(boxPositions, boxPositions.length,
                                    goalPositions, goalCount);
                            if (h == Dijkstra.INF) continue;
                        }

                        visited.put(key, true);
                        dfStack.push(next);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calcula la profundidad del estado en el arbol de busqueda.
     * @param state estado cuya profundidad se calcula
     * @return numero de pasos desde el estado inicial
     */
    private int getDepth(GameState state) {
        int depth = 0;
        GameState current = state;
        while (current.getLastState() != null) {
            depth++;
            current = current.getLastState();
        }
        return depth;
    }

    /**
     * Verifica si todas las cajas del estado estan sobre posiciones objetivo.
     * @param state estado a evaluar
     * @return true si el nivel esta completado
     */
    private boolean isGoalState(GameState state) {
        CustomIterator<Box> it = state.getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            Cell cell = board.getCell(box.getPosition().getX(), box.getPosition().getY());
            if (cell == null || !cell.isGoal()) return false;
        }
        return true;
    }

    /**
     * Aplica un movimiento a un estado simulado.
     * La verificacion de cajas se hace sobre el estado simulado,
     * no sobre el tablero fisico, para evitar inconsistencias durante la busqueda.
     * @param state     estado actual del juego simulado
     * @param direction direccion del movimiento a aplicar
     * @return nuevo GameState resultante, o null si el movimiento es invalido
     */
    private GameState applyMove(GameState state, String direction) {
        int offsetX = 0, offsetY = 0;
        switch (direction.toUpperCase()) {
            case "UP":    offsetY = -1; break;
            case "DOWN":  offsetY =  1; break;
            case "LEFT":  offsetX = -1; break;
            case "RIGHT": offsetX =  1; break;
        }

        int newX = state.getPlayerPosition().getX() + offsetX;
        int newY = state.getPlayerPosition().getY() + offsetY;


        Cell targetCell = board.getCell(newX, newY);
        if (targetCell == null || targetCell.getType() == CellType.WALL) return null;


        Box boxAtTarget = findBoxAt(state.getBoxes(), newX, newY);
        LinkedList<Box> updatedBoxes;

        if (boxAtTarget != null) {
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;

            // Verificar que la celda detrás de la caja no sea pared
            Cell cellBehindBox = board.getCell(boxNewX, boxNewY);
            if (cellBehindBox == null || cellBehindBox.getType() == CellType.WALL) return null;

            // Verificar que no haya otra caja detrás (estado simulado)
            if (findBoxAt(state.getBoxes(), boxNewX, boxNewY) != null) return null;

            updatedBoxes = buildBoxes(state.getBoxes(), boxAtTarget,
                    new Position(boxNewX, boxNewY));
        } else {
            updatedBoxes = buildBoxes(state.getBoxes(), null, null);
        }

        return new GameState(new Position(newX, newY), updatedBoxes, state, direction);
    }

    /**
     * Busca una caja en la posicion dada dentro de la lista del estado simulado.
     * @param boxes lista de cajas del estado simulado
     * @param x     coordenada X a buscar
     * @param y     coordenada Y a buscar
     * @return la caja encontrada, o null si no hay ninguna en esa posicion
     */
    private Box findBoxAt(LinkedList<Box> boxes, int x, int y) {
        CustomIterator<Box> it = boxes.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (box.getPosition().getX() == x && box.getPosition().getY() == y) return box;
        }
        return null;
    }

    /**
     * Construye una nueva lista de cajas aplicando el movimiento de una caja.
     * @param currentBoxes lista de cajas del estado actual
     * @param oldBox       caja que se mueve, o null si no hay empuje
     * @param newBoxPos    nueva posicion de la caja movida, o null si no hay empuje
     * @return nueva lista de cajas con la posicion actualizada
     */
    private LinkedList<Box> buildBoxes(LinkedList<Box> currentBoxes,
                                       Box oldBox, Position newBoxPos) {
        LinkedList<Box> result = new LinkedList<>();
        CustomIterator<Box> it = currentBoxes.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (oldBox != null
                    && box.getPosition().getX() == oldBox.getPosition().getX()
                    && box.getPosition().getY() == oldBox.getPosition().getY()) {
                result.add(new Box(new Position(newBoxPos.getX(), newBoxPos.getY())));
            } else {
                result.add(new Box(new Position(
                        box.getPosition().getX(), box.getPosition().getY())));
            }
        }
        return result;
    }

    /**
     * Reconstruye el camino de movimientos desde el estado final hasta el inicial.
     * @param finalState estado donde se encontro la solucion
     * @return lista de movimientos en orden de ejecucion
     */
    private LinkedList<String> reconstructPath(GameState finalState) {
        Stack<String> reversedStack = new Stack<>();
        GameState current = finalState;
        while (current.getMoveGenerated() != null) {
            reversedStack.push(current.getMoveGenerated());
            current = current.getLastState();
        }
        LinkedList<String> path = new LinkedList<>();
        while (!reversedStack.isEmpty()) {
            path.addLast(reversedStack.pop());
        }
        return path;
    }

    /**
     * Convierte la lista de cajas a un array de posiciones para la heuristica.
     * @param boxes lista de cajas del estado simulado
     * @return array de posiciones de las cajas
     */
    private Position[] boxesToPositionArray(LinkedList<Box> boxes) {
        Position[] result = new Position[boxes.getLength()];
        CustomIterator<Box> it = boxes.iterator();
        int i = 0;
        while (it.hasNext()) {
            Box box = it.next().getData();
            result[i++] = new Position(box.getPosition().getX(), box.getPosition().getY());
        }
        return result;
    }

    /**
     * Construye el estado inicial del solver desde el juego actual.
     * @param game instancia del juego en curso
     * @return GameState con la posicion actual del jugador y las cajas
     */
    public GameState buildInitialState(Game game) {
        Position playerPos = new Position(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY());
        LinkedList<Box> initialBoxes = new LinkedList<>();
        CustomIterator<Box> it = game.getBoard().getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            initialBoxes.add(new Box(new Position(
                    box.getPosition().getX(),
                    box.getPosition().getY())));
        }
        return new GameState(playerPos, initialBoxes, null, null);
    }

    /**
     * Carga las posiciones de las celdas objetivo desde el tablero.
     */
    private void loadGoalPositions() {
        int count = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) count++;
            }
        }
        goalCount = count;
        goalPositions = new Position[count];
        int idx = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) goalPositions[idx++] = new Position(x, y);
            }
        }
    }
}