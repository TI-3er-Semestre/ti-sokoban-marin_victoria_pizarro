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
                        // Poda Floyd-Warshall
                        if (fw != null) {
                            Position[] boxPositions = boxesToPositionArray(next.getBoxes());
                            int h = fw.computeHeuristic(boxPositions, boxPositions.length,
                                    goalPositions, goalCount);
                            if (h == FloydWarshall.INF) continue;
                        }
                        // Poda Dijkstra
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

    private int getDepth(GameState state) {
        int depth = 0;
        GameState current = state;
        while (current.getLastState() != null) {
            depth++;
            current = current.getLastState();
        }
        return depth;
    }

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
     *
     * FIX CRITICO: igual que BFSAuto, la verificación de cajas se hace
     * sobre el ESTADO SIMULADO (LinkedList<Box> del GameState), no sobre
     * board.isFree() que consulta el tablero físico real.
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

        // Paredes nunca cambian — sí usamos el board para eso
        Cell targetCell = board.getCell(newX, newY);
        if (targetCell == null || targetCell.getType() == CellType.WALL) return null;

        // Verificar caja en destino usando el ESTADO SIMULADO
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

    private Box findBoxAt(LinkedList<Box> boxes, int x, int y) {
        CustomIterator<Box> it = boxes.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (box.getPosition().getX() == x && box.getPosition().getY() == y) return box;
        }
        return null;
    }

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