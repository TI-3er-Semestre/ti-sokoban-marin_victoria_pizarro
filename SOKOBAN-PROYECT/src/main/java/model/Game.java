package model;

import structures.BinarySearchTree;
import structures.LinkedList;

public class Game {

    private Board board;
    private Player player;
    private Level currentLevel;
    private GameStats stats;
    private User user;
    private boolean isFinished;
    private BinarySearchTree<GameStats> statsTree;
    private LinkedList<Level> levels;


    public Game(Level level, User user) {
        this.currentLevel = level;
        this.user = user;
        this.isFinished = false;
        this.statsTree = new BinarySearchTree<>();
        this.levels = new LinkedList<>();
        this.levels.add(level);
    }
    /**
     * Agrega un nivel a la lista de niveles disponibles.
     * @param level nivel a agregar
     */
    public void addLevel(Level level) {
        levels.add(level);
    }

    /**
     * Cambia el nivel actual por el nivel con el id indicado.
     * Recorre la lista enlazada buscando el nivel correspondiente.
     * @param levelId id del nivel a cargar
     * @return true si el nivel fue encontrado y cargado, false si no existe
     */
    public boolean loadLevel(int levelId) {
        structures.CustomIterator<Level> iterator = levels.iterator();
        while (iterator.hasNext()) {
            Level level = iterator.next().getData();
            if (level.getId() == levelId) {
                this.currentLevel = level;
                startGame();
                return true;
            }
        }
        return false;
    }
    /**
     * Inicia el juego inicializando tablero y jugador.
     */
    public void startGame() {
        this.board = currentLevel.getBoard();
        this.player = new Player(new Position(1,1));
        this.stats = new GameStats(currentLevel.getId());
        this.isFinished = false;
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


        if (nextCell.hasBox()) {
            Box box = findBoxAt(newX, newY);
            if (box == null || !canPushBox(box, offsetX, offsetY)) {
                return;
            }
            pushBox(box, offsetX, offsetY);
        }


        player.getPosition().setX(newX);
        player.getPosition().setY(newY);
        stats.addMove();
    }

    /**
     * Busca la caja ubicada en la posición indicada recorriendo la lista del tablero.
     * @param x coordenada X
     * @param y coordenada Y
     * @return la caja en esa posición, o null si no se encuentra
     */
    private Box findBoxAt(int x, int y) {
        structures.CustomIterator<Box> iterator = board.getBoxes().iterator();
        while (iterator.hasNext()) {
            Box box = iterator.next().getData();
            if (box.getPosition().getX() == x && box.getPosition().getY() == y) {
                return box;
            }
        }
        return null;
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

        box.getPosition().setX(newX);
        box.getPosition().setY(newY);

        stats.addPush();
    }

    /**
     * Actualiza el estado del juego.
     */
    public void updateGame() {
        if (checkWin()) {
            isFinished = true;
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
}