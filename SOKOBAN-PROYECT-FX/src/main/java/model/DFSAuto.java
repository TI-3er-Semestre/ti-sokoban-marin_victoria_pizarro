package model;

import structures.Stack;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;


public class DFSAuto {

    private Board board;
    private FloydWarshall fw;
    private Position[] goalPositions;
    private int goalCount;
    private Dijkstra dijkstra;

    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};
    private static final int MAX_DEPTH = 500;

    /**
     * Constructor sin Floyd-Warshall.
     * @param board tablero del nivel a resolver
     */
    public DFSAuto(Board board) {
        this.board = board;
        this.fw = null;
        this.dijkstra = null;
        loadGoalPositions();
    }

    /**
     * Constructor con Floyd-Warshall.
     * @param board tablero del nivel a resolver
     * @param fw    instancia de FloydWarshall para ese tablero
     */
    public DFSAuto(Board board, FloydWarshall fw) {
        this.board = board;
        this.fw = fw;
        loadGoalPositions();
    }
    public DFSAuto(Board board, Dijkstra dijkstra) {
        this.board = board;
        this.fw = null;
        this.dijkstra = dijkstra;
        loadGoalPositions();
    }

    /**
     * Carga las posiciones objetivo del tablero en goalPositions.
     */
    private void loadGoalPositions() {
        int count = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) count++;
            }
        }
        this.goalCount = count;
        this.goalPositions = new Position[count];
        int idx = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) {
                    goalPositions[idx++] = new Position(x, y);
                }
            }
        }
    }

    /**
     * Resuelve el nivel usando DFS iterativo con Stack.
     * @param initialState estado del juego al momento de llamar al solver
     * @return lista de movimientos en orden, o null si no hay solución
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
                        visited.put(key, true);
                        dfStack.push(next);
                        if (dijkstra != null) {

                            Position[] boxPositions =
                                    boxesToPositionArray(next.getBoxes());

                            int h = dijkstra.computeHeuristic(
                                    boxPositions,
                                    boxPositions.length,
                                    goalPositions,
                                    goalCount);

                            if (h == Dijkstra.INF) {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Cuenta los pasos desde la raíz hasta el estado dado.
     * @param state estado cuya profundidad se calcula
     * @return profundidad del estado
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
     * Verifica si todas las cajas están sobre celdas GOAL.
     * @param state estado a verificar
     * @return true si el nivel está completado
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
     * Aplica un movimiento a un estado y retorna el estado resultante.
     * @param state     estado actual
     * @param direction dirección a aplicar ("UP","DOWN","LEFT","RIGHT")
     * @return nuevo GameState, o null si el movimiento es inválido
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
        if (targetCell == null || !targetCell.isWalkable()) return null;

        Box boxAtTarget = findBoxAt(state.getBoxes(), newX, newY);
        LinkedList<Box> updatedBoxes;

        if (boxAtTarget != null) {
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;
            if (!board.isFree(boxNewX, boxNewY)) return null;
            if (findBoxAt(state.getBoxes(), boxNewX, boxNewY) != null) return null;
            updatedBoxes = buildBoxes(state.getBoxes(), boxAtTarget, new Position(boxNewX, boxNewY));
        } else {
            updatedBoxes = buildBoxes(state.getBoxes(), null, null);
        }

        return new GameState(new Position(newX, newY), updatedBoxes, state, direction);
    }

    /**
     * Busca una caja en las coordenadas dadas dentro de la lista de cajas.
     * @param boxes lista de cajas del estado
     * @param x     coordenada X
     * @param y     coordenada Y
     * @return la caja encontrada, o null si no existe
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
     * Copia la lista de cajas aplicando el desplazamiento a la caja empujada.
     * @param currentBoxes lista de cajas del estado padre
     * @param oldBox       caja empujada (null si no hubo empuje)
     * @param newBoxPos    nueva posición de la caja (null si no hubo empuje)
     * @return nueva lista de cajas actualizada
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
                result.add(new Box(new Position(box.getPosition().getX(), box.getPosition().getY())));
            }
        }
        return result;
    }

    /**
     * Reconstruye la secuencia de movimientos desde el estado final hasta la raíz.
     * @param finalState estado objetivo encontrado
     * @return lista de movimientos en orden de ejecución
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
     * Convierte la lista de cajas a un array de posiciones.
     * @param boxes lista de cajas del estado
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
     * Construye el GameState inicial a partir del estado actual del juego.
     * @param game instancia del juego en curso
     * @return GameState raíz listo para pasarle a solve()
     */
    public GameState buildInitialState(Game game) {
        Position playerPos = new Position(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY()
        );
        LinkedList<Box> initialBoxes = new LinkedList<>();
        CustomIterator<Box> it = game.getBoard().getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            initialBoxes.add(new Box(new Position(
                    box.getPosition().getX(),
                    box.getPosition().getY()
            )));
        }
        return new GameState(playerPos, initialBoxes, null, null);
    }
}