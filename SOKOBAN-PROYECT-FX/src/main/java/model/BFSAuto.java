package model;

import structures.Queue;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;

public class BFSAuto {
    private int getCount;

    private Board board;
    private Dijkstra dijkstra;


    private FloydWarshall fw;


    private Position[] goalPositions;
    private int goalCount;

    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};


    /**
     * @param board tablero del nivel a resolver
     */
    public BFSAuto(Board board, Dijkstra dijkstra) {
        this.board = board;
        this.fw = null;
        this.goalPositions = null;
        this.goalCount = 0;
        this.dijkstra = dijkstra;
    }


    /**
     *
     * @param board tablero del nivel a resolver
     * @param fw    instancia ya construida de FloydWarshall para ese tablero
     */
    public BFSAuto(Board board, FloydWarshall fw) {
        this.board = board;
        this.fw = fw;


        int count = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) {
                    count++;
                }
            }
        }

        // Segunda pasada: guardarlos en el array.
        this.goalCount = count;
        this.goalPositions = new Position[count];
        int idx = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell != null && cell.isGoal()) {
                    goalPositions[idx] = new Position(x, y);
                    idx++;
                }
            }
        }
    }


    /**
     * @param estadoInicial estado del juego al momento de llamar al solver
     * @return LinkedList<String> con los movimientos en orden ("UP","DOWN",...),
     *         o null si no hay solucion
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

                        //Poda
                        if (fw != null) {
                            Position[] cajasPos = boxesToPositionArray(estadoSiguiente.getBoxes());
                            int h = fw.computeHeuristic(cajasPos, cajasPos.length,
                                    goalPositions, goalCount);
                            if (h == FloydWarshall.INF) {
                                continue;
                            }
                        }
                        if (dijkstra != null) {
                            Position[] cajaPos =
                                    boxesToPositionArray(estadoSiguiente.getBoxes());

                            int h = dijkstra.computeHeuristic(
                                    cajaPos,
                                    cajaPos.length,
                                    goalPositions,
                                    goalCount);

                            if (h == Dijkstra.INF) {
                                continue;
                            }
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
     * @param estado estado a verificar
     * @return true si todas las cajas estan en celdas GOAL
     */
    private boolean isGoalState(GameState estado) {
        CustomIterator<Box> it = estado.getBoxes().iterator();

        while (it.hasNext()) {
            Box box = it.next().getData();
            Cell cell = board.getCell(
                    box.getPosition().getX(),
                    box.getPosition().getY()
            );
            if (cell == null || !cell.isGoal()) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param estado    estado actual del que se parte
     * @param direction direccion a aplicar
     * @return nuevo GameState, o null si el movimiento es invalido
     */
    private GameState applyMove(GameState estado, String direction) {

        int offsetX = 0;
        int offsetY = 0;
        switch (direction.toUpperCase()) {
            case "UP":    offsetY = -1; break;
            case "DOWN":  offsetY =  1; break;
            case "LEFT":  offsetX = -1; break;
            case "RIGHT": offsetX =  1; break;
        }

        int playerX = estado.getPlayerPosition().getX();
        int playerY = estado.getPlayerPosition().getY();
        int newX    = playerX + offsetX;
        int newY    = playerY + offsetY;

        Cell celdaDestino = board.getCell(newX, newY);
        if (celdaDestino == null || !celdaDestino.isWalkable()) {
            return null;
        }

        Box cajaEnDestino = findBoxAt(estado.getBoxes(), newX, newY);
        LinkedList<Box> nuevasCajas;

        if (cajaEnDestino != null) {
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;

            if (!board.isFree(boxNewX, boxNewY)) {
                return null;
            }
            if (findBoxAt(estado.getBoxes(), boxNewX, boxNewY) != null) {
                return null;
            }

            nuevasCajas = buildBoxes(estado.getBoxes(), cajaEnDestino,
                    new Position(boxNewX, boxNewY));
        } else {
            nuevasCajas = buildBoxes(estado.getBoxes(), null, null);
        }

        Position nuevaPosicionJugador = new Position(newX, newY);
        return new GameState(nuevaPosicionJugador, nuevasCajas, estado, direction);
    }

    /**
     * @param cajas lista de cajas del estado simulado
     * @param x     coordenada X a buscar
     * @param y     coordenada Y a buscar
     * @return la Box encontrada, o null si no hay caja en esa posicion
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
     * @param cajasActuales lista de cajas del estado padre
     * @param cajaVieja     caja a reemplazar (null si no hay empuje)
     * @param cajaNuevaPos  posicion nueva de la caja (null si no hay empuje)
     * @return nueva LinkedList<Box> con las cajas actualizadas
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
                resultado.add(new Box(new Position(cajaNuevaPos.getX(),
                        cajaNuevaPos.getY())));
            } else {
                resultado.add(new Box(new Position(box.getPosition().getX(),
                        box.getPosition().getY())));
            }
        }

        return resultado;
    }

    /**
     * @param estadoFinal estado objetivo desde el que se reconstruye
     * @return LinkedList<String> con movimientos en orden de ejecucion
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
     *
     * @param cajas LinkedList de Box del estado simulado
     * @return      Array con las posiciones de todas las cajas
     */
    private Position[] boxesToPositionArray(LinkedList<Box> cajas) {
        Position[] result = new Position[cajas.getLength()];
        CustomIterator<Box> it = cajas.iterator();
        int i = 0;
        while (it.hasNext()) {
            Box box = it.next().getData();
            result[i] = new Position(box.getPosition().getX(),
                    box.getPosition().getY());
            i++;
        }
        return result;
    }

    /**
     * Construye el GameState inicial a partir del estado actual de Game.
     *
     * Uso desde el controlador:
     *   BFSAuto solver = new BFSAuto(game.getBoard());
     *   GameState inicial = solver.buildInitialState(game);
     *   LinkedList<String> solucion = solver.solve(inicial);
     *
     * @param game instancia del juego con el estado actual
     * @return GameState raiz listo para pasarle a solve()
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
    private void loadGoalPositions() {
        int count = 0;
        for (int x = 0; x< board.getWidth(); x ++){
            for (int y = 0; y< board.getHeight(); y++){
                Cell cell = board.getCell(x, y);
                if(cell != null && cell.isGoal()){
                    count ++;
                }
            }
        }
        goalCount = count;
        goalPositions = new Position[count];
        int index = 0;
        for (int x = 0; x < board.getWidth(); x ++){
            for(int y = 0; y< board.getHeight(); y ++){
                Cell cell = board.getCell(x, y);
                if(cell != null && cell.isGoal()){
                    goalPositions[index++] = new Position(x,y);
                }
            }
        }
    }
}