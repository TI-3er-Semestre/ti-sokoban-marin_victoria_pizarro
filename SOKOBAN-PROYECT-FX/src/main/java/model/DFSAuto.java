package model;

import structures.Stack;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;

/**
 * Solver automático del Sokoban usando DFS con pila explícita.
 * Usa HashTable para evitar re-explorar estados y FloydWarshall opcional.
 */
public class DFSAuto {

    private Board board;
    private FloydWarshall fw;
    private Position[] goalPositions;
    private int goalCount;

    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};
    private static final int MAX_DEPTH = 500;

    /**
     * Constructor sin FloydWarshall.
     * @param board tablero del nivel a resolver
     */
    public DFSAuto(Board board) {
        this.board = board;
        this.fw = null;
        loadGoalPositions();
    }

    /**
     * Constructor con FloydWarshall.
     * @param board tablero del nivel a resolver
     * @param fw    instancia de FloydWarshall para ese tablero
     */
    public DFSAuto(Board board, FloydWarshall fw) {
        this.board = board;
        this.fw = fw;
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
     * @param estadoInicial estado del juego al momento de llamar al solver
     * @return lista de movimientos en orden, o null si no hay solución
     */
    public LinkedList<String> solve(GameState estadoInicial) {
        Stack<GameState> pilaDFS = new Stack<>();
        HashTable<String, Boolean> visitados = new HashTable<>(1024);

        pilaDFS.push(estadoInicial);
        visitados.put(estadoInicial.generateKey(), true);

        while (!pilaDFS.isEmpty()) {
            GameState estadoActual = pilaDFS.pop();

            if (getDepth(estadoActual) > MAX_DEPTH) continue;

            if (isGoalState(estadoActual)) {
                return reconstructPath(estadoActual);
            }


            for (int i = DIRECTIONS.length - 1; i >= 0; i--) {
                GameState estadoSiguiente = applyMove(estadoActual, DIRECTIONS[i]);

                if (estadoSiguiente != null) {
                    String clave = estadoSiguiente.generateKey();

                    if (!visitados.containsKey(clave)) {
                        if (fw != null) {
                            Position[] cajasPos = boxesToPositionArray(estadoSiguiente.getBoxes());
                            int h = fw.computeHeuristic(cajasPos, cajasPos.length,
                                    goalPositions, goalCount);
                            if (h == FloydWarshall.INF) continue;
                        }
                        visitados.put(clave, true);
                        pilaDFS.push(estadoSiguiente);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Cuenta los pasos desde la raíz hasta el estado dado.
     * @param estado estado cuya profundidad se calcula
     * @return profundidad del estado
     */
    private int getDepth(GameState estado) {
        int depth = 0;

        GameState current = estado;
        while (current.getLastState() != null) {
            depth++;
            current = current.getLastState();
        }

        return depth;
    }

    /**
     * Verifica si todas las cajas están sobre celdas GOAL.
     * @param estado estado a verificar
     * @return true si el nivel está completado
     */
    private boolean isGoalState(GameState estado) {
        CustomIterator<Box> it = estado.getBoxes().iterator();

        while (it.hasNext()) {
            Box box = it.next().getData();
            Cell cell = board.getCell(box.getPosition().getX(), box.getPosition().getY());
            if (cell == null || !cell.isGoal()) return false;
        }

        return true;
    }

    /**
     * Aplica un movimiento a un estado y retorna el estado resultante.
     * @param estado    estado actual
     * @param direction dirección a aplicar ("UP","DOWN","LEFT","RIGHT")
     * @return nuevo GameState, o null si el movimiento es inválido
     */
    private GameState applyMove(GameState estado, String direction) {

        int offsetX = 0, offsetY = 0;
        switch (direction.toUpperCase()) {
            case "UP":    offsetY = -1; break;
            case "DOWN":  offsetY =  1; break;
            case "LEFT":  offsetX = -1; break;
            case "RIGHT": offsetX =  1; break;
        }

        int newX = estado.getPlayerPosition().getX() + offsetX;
        int newY = estado.getPlayerPosition().getY() + offsetY;

        Cell celdaDestino = board.getCell(newX, newY);
        if (celdaDestino == null || !celdaDestino.isWalkable()) return null;

        Box cajaEnDestino = findBoxAt(estado.getBoxes(), newX, newY);
        LinkedList<Box> nuevasCajas;

        if (cajaEnDestino != null) {
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;
            if (!board.isFree(boxNewX, boxNewY)) return null;
            if (findBoxAt(estado.getBoxes(), boxNewX, boxNewY) != null) return null;
            nuevasCajas = buildBoxes(estado.getBoxes(), cajaEnDestino, new Position(boxNewX, boxNewY));
        } else {
            nuevasCajas = buildBoxes(estado.getBoxes(), null, null);
        }

        return new GameState(new Position(newX, newY), nuevasCajas, estado, direction);
    }

    /**
     * Busca una caja en las coordenadas dadas dentro de la lista de cajas.
     * @param cajas lista de cajas del estado
     * @param x     coordenada X
     * @param y     coordenada Y
     * @return la caja encontrada, o null si no existe
     */
    private Box findBoxAt(LinkedList<Box> cajas, int x, int y) {
        CustomIterator<Box> it = cajas.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (box.getPosition().getX() == x && box.getPosition().getY() == y) return box;
        }

        return null;
    }

    /**
     * Copia la lista de cajas aplicando el desplazamiento a la caja empujada.
     * @param cajasActuales lista de cajas del estado padre
     * @param cajaVieja     caja empujada (null si no hubo empuje)
     * @param cajaNuevaPos  nueva posición de la caja (null si no hubo empuje)
     * @return nueva lista de cajas actualizada
     */
    private LinkedList<Box> buildBoxes(LinkedList<Box> cajasActuales,
                                       Box cajaVieja, Position cajaNuevaPos) {
        LinkedList<Box> resultado = new LinkedList<>();
        CustomIterator<Box> it = cajasActuales.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (cajaVieja != null
                    && box.getPosition().getX() == cajaVieja.getPosition().getX()
                    && box.getPosition().getY() == cajaVieja.getPosition().getY()) {
                resultado.add(new Box(new Position(cajaNuevaPos.getX(), cajaNuevaPos.getY())));

            } else {
                resultado.add(new Box(new Position(box.getPosition().getX(), box.getPosition().getY())));
            }
        }

        return resultado;
    }

    /**
     * Reconstruye la secuencia de movimientos desde el estado final hasta la raíz.
     * @param estadoFinal estado objetivo encontrado
     * @return lista de movimientos en orden de ejecución
     */
    private LinkedList<String> reconstructPath(GameState estadoFinal) {

        Stack<String> pilaInversa = new Stack<>();
        GameState actual = estadoFinal;
        while (actual.getMoveGenerated() != null) {
            pilaInversa.push(actual.getMoveGenerated());
            actual = actual.getLastState();
        }
        LinkedList<String> camino = new LinkedList<>();
        while (!pilaInversa.isEmpty()) {
            camino.addLast(pilaInversa.pop());
        }

        return camino;
    }

    /**
     * Convierte la lista de cajas a un array de posiciones.
     * @param cajas lista de cajas del estado
     * @return array de posiciones de las cajas
     */
    private Position[] boxesToPositionArray(LinkedList<Box> cajas) {
        Position[] result = new Position[cajas.getLength()];
        CustomIterator<Box> it = cajas.iterator();
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
        Position posJugador = new Position(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY()

        );
        LinkedList<Box> cajasIniciales = new LinkedList<>();
        CustomIterator<Box> it = game.getBoard().getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            cajasIniciales.add(new Box(new Position(
                    box.getPosition().getX(),
                    box.getPosition().getY()
            )));
        }
        return new GameState(posJugador, cajasIniciales, null, null);
    }
}