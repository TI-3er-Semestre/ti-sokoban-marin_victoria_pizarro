package model;

import javafx.scene.control.Label;
import structures.*;

public class Game {
    private GameTimer timer;
    private Stack<GameState> history;
    private Board board;
    private Player player;
    private Level currentLevel;
    private GameStats stats;
    private User user;
    private boolean isFinished;
    private BinarySearchTree<GameStats> statsTree;
    private LinkedList<Level> levels;
    private Queue<String> moveHistory;
    private HashTable<Integer, Level> levelsTable;
    private HashTable<String, Box> boxMap;

    public Game(Level level, User user) {
        this.currentLevel = level;
        this.user = user;
        this.isFinished = false;
        this.statsTree = new BinarySearchTree<>();
        this.levels = new LinkedList<>();
        this.moveHistory = new Queue<>();
        this.levelsTable = new HashTable<>(10);
        this.boxMap      = new HashTable<>(50);

        levels.add(level);
        levelsTable.put(level.getId(), level);
    }
    /**
     * Agrega un nivel a la lista enlazada y a la tabla hash de niveles.
     * Ambas estructuras se mantienen sincronizadas.
     * @param level nivel a agregar
     */
    public void addLevel(Level level) {
        levels.add(level);
        levelsTable.put(level.getId(), level);
    }

    /**
     * Carga el nivel con el id dado usando la tabla hash
     * Si el nivel existe, lo establece como nivel actual e inicia el juego.
     * @param levelId identificador del nivel a cargar
     * @return true si el nivel fue encontrado y cargado, false si no existe
     */
    public boolean loadLevel(int levelId) {
        Level level = levelsTable.get(levelId);
        if (level != null) {
            this.currentLevel = level;
            startGame();
            return true;
        }
        return false;
    }
    /**
     * Inicia el juego inicializando tablero y jugador.
     */
    public void startGame() {
        this.board = currentLevel.getBoard();
        Position start = currentLevel.getPlayerStart();
        this.player = new Player(new Position(start.getX(), start.getY()));
        this.stats = new GameStats(currentLevel.getId());
        if (this.user != null) {
            this.stats.setPlayerName(this.user.getUsername());
        }


        this.isFinished = false;

        history = new Stack<>();


        this.moveHistory = new Queue<>();
        this.boxMap      = new HashTable<>(50);


        structures.CustomIterator<Box> it = board.getBoxes().iterator();
        while (it.hasNext()) {
            Box box = it.next().getData();
            String key = boxKey(box.getPosition().getX(), box.getPosition().getY());
            boxMap.put(key, box);
        }

    }


    /**
     * Mueve al jugador en la dirección indicada.
     * Si hay una caja en la celda destino, intenta empujarla.
     * Si no se puede empujar, el jugador no se mueve.
     * @param direction dirección del movimiento (UP, DOWN, LEFT, RIGHT)
     */
    public void movePlayer(String direction) {

        int offsetX = 0;
        int offsetY = 0;

        switch (direction.toUpperCase()) {
            case "UP":    offsetY = -1;
            break;
            case "DOWN":  offsetY =  1;
            break;
            case "LEFT":  offsetX = -1;
            break;
            case "RIGHT": offsetX =  1;
            break;
        }

        int currentX = player.getPosition().getX();
        int currentY = player.getPosition().getY();
        int newX = currentX + offsetX;
        int newY = currentY + offsetY;

        Cell nextCell = board.getCell(newX, newY);


        if (nextCell == null || !nextCell.isWalkable()) {
            return;
        }
        // valida caja sin moverla
        Box box = null;
        // validar sin modificar

        if (nextCell.hasBox()) {
            box = findBoxAt(newX, newY);
            if (box == null || !canPushBox(box, offsetX, offsetY)) {
                return;
            }

        }// guardar estado
        history.push(new GameState(
                new Position(currentX, currentY),
                copyBoxes()
        ));
        // aplicar cambios
        if (box != null) {
            pushBox(box, offsetX, offsetY);
        }



        moveHistory.enqueue(direction);


        player.getPosition().setX(newX);
        player.getPosition().setY(newY);
        stats.addMove();

    }

    /**
     * Busca la caja en la posición dada usando la tabla hash
     * @param x coordenada X de la posición
     * @param y coordenada Y de la posición
     * @return la caja en esa posición, o null si no hay ninguna
     */
    private Box findBoxAt(int x, int y) {
        return boxMap.get(boxKey(x, y));
    }

    /**
     * Verifica si una caja puede ser empujada en la dirección dada.
     * Delega en board.isFree() que ya valida límites + caminable + sin caja.
     * @param box     caja a verificar
     * @param offsetX desplazamiento en X
     * @param offsetY desplazamiento en Y
     * @return true si la celda destino está libre
     */
    private boolean canPushBox(Box box, int offsetX, int offsetY) {
        int targetX = box.getPosition().getX() + offsetX;
        int targetY = box.getPosition().getY() + offsetY;
        return board.isFree(targetX, targetY);
    }

    /**
     * crea una copia de la lista de cajas del tablero
     * @return una nueva linkedlist con copia de todas las cajas
     * el tablero original no se modifica
     * la lista retorna el mismo tabmano que board.getboses
     * cada caja copiada tiene las mismas coordenadas que su original
     *
     */



    private LinkedList<Box> copyBoxes() {
        LinkedList<Box> copy = new LinkedList<>();
        structures.CustomIterator<Box> it = board.getBoxes().iterator();

        while (it.hasNext()) {
            Box b = it.next().getData();
            Position pos = new Position(b.getPosition().getX(), b.getPosition().getY());
            copy.add(new Box(pos));
        }

        return copy;
    }

    /**
     * Empuja una caja a la celda destino.
     * Solo llamar después de verificar con canPushBox().
     * @param box     caja a mover
     * @param offsetX desplazamiento en X
     * @param offsetY desplazamiento en Y
     */
    private void pushBox(Box box, int offsetX, int offsetY) {
        int oldX = box.getPosition().getX();
        int oldY = box.getPosition().getY();

        int newX = oldX + offsetX;
        int newY = oldY + offsetY;

        board.getCell(oldX, oldY).setHasBox(false);
        board.getCell(newX, newY).setHasBox(true);

        boxMap.remove(boxKey(oldX, oldY));
        box.getPosition().setX(newX);
        box.getPosition().setY(newY);
        boxMap.put(boxKey(newX, newY), box);

        stats.addPush();
    }
    /**
     * Genera la clave string para la tabla hash de cajas a partir de coordenadas.
     * @param x coordenada X
     * @param y coordenada Y
     * @return clave en formato "x,y"
     */
    private String boxKey(int x, int y) {
        return x + "," + y;
    }

    /**
     * Actualiza el estado del juego.
     */
    public void updateGame() {
        if (checkWin()) {
            isFinished = true;
            if (timer!= null){
                timer.stop();
            }
        }
    }
    // metodo deshacer movimiento
    public void undoMove() {
        if (!history.isEmpty()) {
            GameState prev = history.pop();

            // retaurar al jiugador
            player.setPosition(prev.getPlayerPosition());
            // restaurar las cajas
            LinkedList<Box> prevBoxes = prev.getBoxes();
            //limpiar el tablero actual
            structures.CustomIterator<Box> it = board.getBoxes().iterator();
            while(it.hasNext()){
                Box b = it.next().getData();
                board.getCell(
                        b.getPosition().getX(),
                        b.getPosition().getY()).setHasBox(false);
            }
            // asignar cajas anteriores
            board.setBoxes(prevBoxes);
            // marcar cajas en el tablero
            structures.CustomIterator<Box> it2 = prevBoxes.iterator();
            while (it2.hasNext()){
                Box b = it2.next().getData();
                board.getCell(b.getPosition().getX(), b.getPosition().getY()).setHasBox(true);
            }
            boxMap = new HashTable<>(50);

            structures.CustomIterator<Box> it3 = prevBoxes.iterator();
            while (it3.hasNext()) {
                Box b = it3.next().getData();
                boxMap.put(boxKey(b.getPosition().getX(), b.getPosition().getY()), b);
            }
            stats.removeMove();





        }
    }

    /**
     * Verifica si el jugador ganó.
     * @return true si el nivel está completo
     */
    public boolean checkWin() {

        return currentLevel.isCompleted();
    }

    /**
     * Reinicia el juego.
     */
    public void resetGame() {
        if (timer != null){
            timer.stop();
        }
        startGame();
    }
    /**
     * Reemplaza el nivel con uno nuevo cargado desde JSON y reinicia el juego.
     * @param levelId    ID del nivel a reemplazar
     * @param freshLevel nivel recien cargado desde JSON
     */
    public void replaceLevelAndRestart(int levelId, Level freshLevel) {
        levelsTable.put(levelId, freshLevel);
        this.currentLevel = freshLevel;
        startGame();
    }

    /**
     * Guarda las estadísticas de la partida actual en el árbol binario.
     * El árbol ordena automáticamente por puntaje (mayor a menor).
     */


    public void saveGame() {
        if (stats != null) {
            statsTree.addNode(stats);
        }
    }

    /**
     * Retorna el árbol de estadísticas para la pantalla de ranking.
     * Llamar statsTree.inorder() entrega las partidas de mejor a peor puntaje.
     * @return árbol binario de búsqueda con las estadísticas registradas
     */
    public BinarySearchTree<GameStats> getStatsTree() {

        return statsTree;
    }

    /**
     * Retorna la cola con el historial de movimientos del jugador.
     * @return cola FIFO con los movimientos en orden de ejecución
     */
    public Queue<String> getMoveHistory() {
        return moveHistory;
    }

    /**
     * Retorna la lista de niveles disponibles.
     * @return lista enlazada de niveles
     */
    public LinkedList<Level> getLevels() {
        return levels;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public GameStats getStats() {
        return stats;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setUser(User user) {
        this.user = user;
    }
}