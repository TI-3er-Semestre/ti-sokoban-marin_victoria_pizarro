package model;

import structures.Queue;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;

public class BFSAuto {

    private Board board;
    private Dijkstra dijkstra;
    private FloydWarshall fw;
    private Position[] goalPositions;
    private int goalCount;

    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};

    public BFSAuto(Board board) {
        this.board = board;
        this.fw = null;
        this.dijkstra = null;
        loadGoalPositions();
    }

    public BFSAuto(Board board, Dijkstra dijkstra) {
        this.board = board;
        this.fw = null;
        this.dijkstra = dijkstra;
        loadGoalPositions();
    }

    public BFSAuto(Board board, FloydWarshall fw) {
        this.board = board;
        this.fw = fw;
        this.dijkstra = null;
        loadGoalPositions();
    }

    /**
     * Resuelve el nivel desde el estado inicial usando BFS.
     * @param estadoInicial estado de partida con posicion del jugador y cajas
     * @return lista de movimientos que resuelven el nivel, o null si no hay solucion
     */
    public LinkedList<String> solve(GameState estadoInicial) {
        Queue<GameState> colaBFS = new Queue<>();
        HashTable<String, Boolean> visitados = new HashTable<>(1024);

        colaBFS.enqueue(estadoInicial);
        visitados.put(estadoInicial.generateKey(), true);

        while (!colaBFS.isEmpty()) {

            GameState estadoActual = colaBFS.dequeue();

            if (isGoalState(estadoActual)) {
                return reconstructPath(estadoActual);
            }

            for (String direction : DIRECTIONS) {
                GameState estadoSiguiente = applyMove(estadoActual, direction);

                if (estadoSiguiente != null) {
                    String clave = estadoSiguiente.generateKey();

                    if (!visitados.containsKey(clave)) {
                        // Poda Floyd-Warshall
                        if (fw != null) {
                            Position[] cajasPos = boxesToPositionArray(estadoSiguiente.getBoxes());
                            int h = fw.computeHeuristic(cajasPos, cajasPos.length,
                                    goalPositions, goalCount);
                            if (h == FloydWarshall.INF) continue;
                        }
                        // Poda Dijkstra
                        if (dijkstra != null) {
                            Position[] cajasPos = boxesToPositionArray(estadoSiguiente.getBoxes());
                            int h = dijkstra.computeHeuristic(cajasPos, cajasPos.length,
                                    goalPositions, goalCount);
                            if (h == Dijkstra.INF) continue;
                        }

                        visitados.put(clave, true);
                        colaBFS.enqueue(estadoSiguiente);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Verifica si todas las cajas del estado estan sobre posiciones objetivo.
     * @param estado estado a evaluar
     * @return true si el nivel esta completado
     */
    private boolean isGoalState(GameState estado) {
        CustomIterator<Box> it = estado.getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            Cell cell = board.getCell(
                    box.getPosition().getX(),
                    box.getPosition().getY());
            if (cell == null || !cell.isGoal()) return false;
        }
        return true;
    }

    /**
     * Aplica un movimiento a un estado simulado.
     * La verificacion de cajas se hace sobre el estado simulado,
     * no sobre el tablero fisico, para evitar inconsistencias durante la busqueda.
     * @param estado    estado actual del juego simulado
     * @param direction direccion del movimiento a aplicar
     * @return nuevo GameState resultante, o null si el movimiento es invalido
     */
    private GameState applyMove(GameState estado, String direction) {
        int offsetX = 0, offsetY = 0;
        switch (direction.toUpperCase()) {
            case "UP":    offsetY = -1; break;
            case "DOWN":  offsetY =  1; break;
            case "LEFT":  offsetX = -1; break;
            case "RIGHT": offsetX =  1; break;
        }

        int playerX = estado.getPlayerPosition().getX();
        int playerY = estado.getPlayerPosition().getY();
        int newX = playerX + offsetX;
        int newY = playerY + offsetY;

        // Verificar límites y que la celda destino sea caminable (pared o fuera = inválido)
        // Aquí SÍ usamos el board porque las paredes nunca cambian
        Cell celdaDestino = board.getCell(newX, newY);
        if (celdaDestino == null || celdaDestino.getType() == CellType.WALL) {
            return null;
        }

        // Verificar si hay una caja en el destino consultando el ESTADO SIMULADO
        Box cajaEnDestino = findBoxAt(estado.getBoxes(), newX, newY);
        LinkedList<Box> nuevasCajas;

        if (cajaEnDestino != null) {
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;

            // Verificar que la celda detrás de la caja exista y no sea pared
            Cell celdaTrasCaja = board.getCell(boxNewX, boxNewY);
            if (celdaTrasCaja == null || celdaTrasCaja.getType() == CellType.WALL) {
                return null;
            }

            // Verificar que no haya OTRA caja en la celda detrás (estado simulado)
            if (findBoxAt(estado.getBoxes(), boxNewX, boxNewY) != null) {
                return null;
            }

            nuevasCajas = buildBoxes(estado.getBoxes(), cajaEnDestino,
                    new Position(boxNewX, boxNewY));
        } else {
            nuevasCajas = buildBoxes(estado.getBoxes(), null, null);
        }

        return new GameState(new Position(newX, newY), nuevasCajas, estado, direction);
    }

    /**
     * Busca una caja en la posicion dada dentro de la lista del estado simulado.
     * @param cajas lista de cajas del estado simulado
     * @param x     coordenada X a buscar
     * @param y     coordenada Y a buscar
     * @return la caja encontrada, o null si no hay ninguna en esa posicion
     */
    private Box findBoxAt(LinkedList<Box> cajas, int x, int y) {
        CustomIterator<Box> it = cajas.iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            if (box.getPosition().getX() == x && box.getPosition().getY() == y) {
                return box;
            }
        }
        return null;
    }

    /**
     * Construye una nueva lista de cajas aplicando el movimiento de una caja.
     * @param cajasActuales lista de cajas del estado actual
     * @param cajaVieja     caja que se mueve, o null si no hay empuje
     * @param cajaNuevaPos  nueva posicion de la caja movida, o null si no hay empuje
     * @return nueva lista de cajas con la posicion actualizada
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
                resultado.add(new Box(new Position(
                        box.getPosition().getX(), box.getPosition().getY())));
            }
        }
        return resultado;
    }

    /**
     * Reconstruye el camino de movimientos desde el estado final hasta el inicial.
     * @param estadoFinal estado donde se encontro la solucion
     * @return lista de movimientos en orden de ejecucion
     */
    private LinkedList<String> reconstructPath(GameState estadoFinal) {
        structures.Stack<String> pilaInversa = new structures.Stack<>();
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
     * Convierte la lista de cajas a un array de posiciones para la heuristica.
     * @param cajas lista de cajas del estado simulado
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
     * Construye el estado inicial del solver desde el juego actual.
     * @param game instancia del juego en curso
     * @return GameState con la posicion actual del jugador y las cajas
     */
    public GameState buildInitialState(Game game) {
        Position posJugador = new Position(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY());

        LinkedList<Box> cajasIniciales = new LinkedList<>();
        CustomIterator<Box> it = game.getBoard().getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            cajasIniciales.add(new Box(new Position(
                    box.getPosition().getX(),
                    box.getPosition().getY())));
        }
        return new GameState(posJugador, cajasIniciales, null, null);
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
        int index = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) goalPositions[index++] = new Position(x, y);
            }
        }
    }
}