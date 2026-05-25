package model;

import structures.Queue;
import structures.HashTable;
import structures.LinkedList;
import structures.CustomIterator;

/**
 * Solucionador automático del Sokoban usando BFS (Breadth-First Search).
 *
 * ── Qué es el grafo de estados ────────────────────────────────────────────────
 * Cada NODO  = un GameState { posición jugador + posiciones de cajas }
 * Cada ARISTA = un movimiento válido (UP, DOWN, LEFT, RIGHT)
 *
 * BFS explora nivel por nivel (primero todos los estados a distancia 1,
 * luego a distancia 2, etc.), lo que garantiza que la primera solución
 * encontrada tiene el MENOR número de movimientos posible.
 *
 * ── Estructuras propias usadas ────────────────────────────────────────────────
 *   Queue<GameState>           → cola BFS (FIFO)
 *   HashTable<String, Boolean> → tabla de visitados (transposición)
 *   LinkedList<String>         → lista de movimientos solución
 *   LinkedList<Box>            → posiciones de cajas por estado
 *
 * ── Complejidad temporal ──────────────────────────────────────────────────────
 * O(b^d) donde b = factor de ramificación (máx. 4 direcciones)
 *              d = profundidad de la solución óptima
 *
 * ── Complejidad espacial ──────────────────────────────────────────────────────
 * ┌──────────────────────────────────┬─────────────────────┬──────────────────────────┐
 * │ Tipo                             │ Variable            │ Cant. valores atómicos   │
 * ├──────────────────────────────────┼─────────────────────┼──────────────────────────┤
 * │ Queue<GameState> (máx. b^d nodos)│ colaBFS             │ b^d × (2 + 2n)           │
 * │ HashTable<String, Boolean>       │ visitados           │ b^d entradas             │
 * │ LinkedList<String> (d pasos)     │ solucion            │ d                        │
 * └──────────────────────────────────┴─────────────────────┴──────────────────────────┘
 * donde n = número de cajas en el nivel.
 */
public class BFSAuto {

    /** Tablero del nivel — solo lectura, para validar movimientos y celdas goal. */
    private Board board;

    /** Direcciones posibles en orden consistente. */
    private static final String[] DIRECTIONS = {"UP", "DOWN", "LEFT", "RIGHT"};

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * @param board tablero del nivel a resolver (no se modifica durante BFS)
     */
    public BFSAuto(Board board) {
        this.board = board;
    }

    // ── solve ─────────────────────────────────────────────────────────────────

    /**
     * Ejecuta BFS desde el estado inicial del juego.
     *
     * Algoritmo:
     *   1. Encolar estado inicial.
     *   2. Marcar estado inicial como visitado.
     *   3. Mientras la cola no esté vacía:
     *      a. Desencolar estado actual.
     *      b. Si es estado objetivo → reconstruir camino y retornar.
     *      c. Para cada dirección: generar estado siguiente.
     *      d. Si el estado siguiente no fue visitado → encolar y marcar.
     *   4. Si la cola se vacía sin solución → retornar null.
     *
     * @param estadoInicial estado del juego al momento de llamar al solver
     * @return LinkedList<String> con los movimientos en orden ("UP","DOWN",...),
     *         o null si no hay solución
     */
    public LinkedList<String> solve(GameState estadoInicial) {

        // Cola BFS — usa nuestra Queue propia (FIFO con nodos enlazados)
        Queue<GameState> colaBFS = new Queue<>();

        // Tabla de transposición — evita re-explorar estados ya visitados
        HashTable<String, Boolean> visitados = new HashTable<>(1024);

        // Encolar estado raíz
        colaBFS.enqueue(estadoInicial);
        visitados.put(estadoInicial.generateKey(), true);

        // ── Bucle BFS principal ──────────────────────────────────────────────
        while (!colaBFS.isEmpty()) {

            GameState estadoActual = colaBFS.dequeue();

            // ¿Llegamos al objetivo?
            if (isGoalState(estadoActual)) {
                return reconstructPath(estadoActual);
            }

            // Expandir las 4 direcciones
            for (String direction : DIRECTIONS) {
                GameState estadoSiguiente = applyMove(estadoActual, direction);

                if (estadoSiguiente != null) {
                    String clave = estadoSiguiente.generateKey();

                    if (!visitados.containsKey(clave)) {
                        visitados.put(clave, true);
                        colaBFS.enqueue(estadoSiguiente);
                    }
                }
            }
        }

        // Sin solución
        return null;
    }

    // ── isGoalState ───────────────────────────────────────────────────────────

    /**
     * Verifica si todas las cajas del estado están sobre celdas objetivo.
     *
     * Replica exactamente la lógica de Level.isCompleted() pero sobre
     * el estado simulado (no el tablero real).
     *
     * Complejidad: O(n) donde n = número de cajas.
     *
     * @param estado estado a verificar
     * @return true si todas las cajas están en celdas GOAL
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

    // ── applyMove ─────────────────────────────────────────────────────────────

    /**
     * Simula la aplicación de un movimiento sobre un estado y retorna
     * el nuevo estado resultante SIN modificar el tablero real.
     *
     * Lógica (igual que Game.movePlayer, pero sobre copias):
     *   1. Calcular (newX, newY) = posición jugador + offset de dirección.
     *   2. Si la celda destino no es caminable → null (movimiento inválido).
     *   3. Si hay una caja en (newX, newY):
     *      a. Calcular (boxNewX, boxNewY) = posición caja + mismo offset.
     *      b. Si esa celda no está libre (board.isFree) → null.
     *      c. Si hay otra caja simulada ahí → null.
     *      d. Si está libre → construir lista con la caja movida.
     *   4. Si no hay caja → copiar lista sin cambios.
     *   5. Retornar nuevo GameState con parent=estadoActual y move=direction.
     *
     * Complejidad: O(n) por buildBoxes (copia de cajas).
     *
     * @param estado    estado actual del que se parte
     * @param direction dirección a aplicar
     * @return nuevo GameState, o null si el movimiento es inválido
     */
    private GameState applyMove(GameState estado, String direction) {

        // Calcular offset
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

        // Validar celda destino del jugador
        Cell celdaDestino = board.getCell(newX, newY);
        if (celdaDestino == null || !celdaDestino.isWalkable()) {
            return null;
        }

        // Buscar si hay una caja simulada en (newX, newY)
        Box cajaEnDestino = findBoxAt(estado.getBoxes(), newX, newY);

        // Construir nueva lista de cajas
        LinkedList<Box> nuevasCajas;

        if (cajaEnDestino != null) {
            // Hay caja — verificar si se puede empujar
            int boxNewX = newX + offsetX;
            int boxNewY = newY + offsetY;

            // Celda destino de la caja debe ser caminable (sin muro ni borde)
            if (!board.isFree(boxNewX, boxNewY)) {
                return null;
            }
            // No debe haber otra caja simulada en el destino
            if (findBoxAt(estado.getBoxes(), boxNewX, boxNewY) != null) {
                return null;
            }

            // Construir lista con la caja movida
            nuevasCajas = buildBoxes(estado.getBoxes(), cajaEnDestino,
                    new Position(boxNewX, boxNewY));
        } else {
            // Sin caja — copiar lista sin cambios
            nuevasCajas = buildBoxes(estado.getBoxes(), null, null);
        }

        Position nuevaPosicionJugador = new Position(newX, newY);
        return new GameState(nuevaPosicionJugador, nuevasCajas, estado, direction);
    }

    // ── findBoxAt ─────────────────────────────────────────────────────────────

    /**
     * Busca si hay una caja en (x, y) dentro de la lista simulada.
     *
     * Complejidad: O(n) donde n = número de cajas.
     *
     * @param cajas lista de cajas del estado simulado
     * @param x     coordenada X a buscar
     * @param y     coordenada Y a buscar
     * @return la Box encontrada, o null si no hay caja en esa posición
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

    // ── buildBoxes ────────────────────────────────────────────────────────────

    /**
     * Construye una nueva LinkedList<Box> copiando las cajas actuales,
     * reemplazando cajaVieja por una nueva caja en cajaNuevaPos si se indica.
     *
     * Si cajaVieja == null, simplemente clona todas las cajas.
     * Usa add() (inserción ordenada por compareTo de Box) para que
     * generateKey() sea siempre determinista.
     *
     * Complejidad: O(n) donde n = número de cajas.
     *
     * @param cajasActuales  lista de cajas del estado padre
     * @param cajaVieja      caja a reemplazar (null si no hay empuje)
     * @param cajaNuevaPos   posición nueva de la caja (null si no hay empuje)
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
                // Esta es la caja que se movió — insertar con nueva posición
                resultado.add(new Box(new Position(cajaNuevaPos.getX(),
                        cajaNuevaPos.getY())));
            } else {
                // Copiar la caja sin cambios
                resultado.add(new Box(new Position(box.getPosition().getX(),
                        box.getPosition().getY())));
            }
        }

        return resultado;
    }

    // ── reconstructPath ───────────────────────────────────────────────────────

    /**
     * Reconstruye la secuencia de movimientos desde el estado goal
     * hasta el estado raíz siguiendo la cadena de parentState.
     *
     * Como se recorre goal → raíz, los movimientos quedan invertidos.
     * Se usa Stack propia para invertirlos y obtener el orden correcto.
     *
     * Complejidad: O(d) donde d = profundidad de la solución.
     *
     * @param estadoFinal estado objetivo desde el que se reconstruye
     * @return LinkedList<String> con movimientos en orden de ejecución
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

    // ── buildInitialState ─────────────────────────────────────────────────────

    /**
     * Construye el GameState inicial a partir del estado actual de Game.
     *
     * Uso desde el controlador:
     *   BFSAuto solver = new BFSAuto(game.getBoard());
     *   GameState inicial = solver.buildInitialState(game);
     *   LinkedList<String> solucion = solver.solve(inicial);
     *
     * @param game instancia del juego con el estado actual
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