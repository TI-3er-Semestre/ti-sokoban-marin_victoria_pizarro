package control;

import model.*;
import structures.CustomIterator;
import structures.HashTable;

public class GameController {

    private Game game;
    private HashTable<String, User> users;
    private User currentUser;

    public GameController() {
        this.users = new HashTable<>(10);
        this.game = null;
        this.currentUser = null;
    }

    /**
     * Inicializa el juego con los tres niveles y el usuario registrado.
     * @param level1 nivel basico
     * @param level2 nivel avanzado
     * @param level3 nivel experto
     * @param user   usuario que jugara la partida
     */
    public void initGame(Level level1, Level level2, Level level3, User user) {
        this.game = new Game(level1, user);
        this.game.addLevel(level2);
        this.game.addLevel(level3);
        addUser(user);
        setCurrentUser(user);
    }

    /**
     * Recarga el nivel desde JSON y reinicia el juego en ese nivel.
     * @param levelId ID del nivel a cargar (1, 2 o 3)
     */
    public void loadFreshLevel(int levelId) {
        String path = "/assets/levels/level" + levelId + ".json";
        Level fresh = LevelLoader.loadLevel(path);
        if (fresh != null && game != null) {
            game.replaceLevelAndRestart(levelId, fresh);
        } else if (game != null) {
            game.loadLevel(levelId);
        }
    }

    /**
     * Restaura el estado guardado en disco sobre el juego actual.
     * Reposiciona al jugador, las cajas y las estadisticas segun el snapshot.
     * @param saved snapshot cargado desde disco
     */
    public void restoreSavedState(SavedState saved) {
        if (game == null || saved == null) return;
        game.loadLevel(saved.getLevelId());
        Board board = game.getBoard();
        CustomIterator<Box> it = board.getBoxes().iterator();
        while (it.hasNext()) {
            Box b = it.next().getData();
            board.getCell(b.getPosition().getX(), b.getPosition().getY()).setHasBox(false);
        }
        structures.LinkedList<Box> newBoxes = new structures.LinkedList<>();
        int[] bx = saved.getBoxX();
        int[] by = saved.getBoxY();
        for (int i = 0; i < bx.length; i++) {
            Box box = new Box(new Position(bx[i], by[i]));
            newBoxes.add(box);
            board.getCell(bx[i], by[i]).setHasBox(true);
        }
        board.setBoxes(newBoxes);
        game.getPlayer().getPosition().setX(saved.getPlayerX());
        game.getPlayer().getPosition().setY(saved.getPlayerY());
        GameStats stats = game.getStats();
        if (stats != null) {
            for (int i = 0; i < saved.getMoves(); i++) { stats.addMove(); }
            for (int i = 0; i < saved.getPushes(); i++) { stats.addPush(); }
            stats.setTime(saved.getTime());
        }
        game.rebuildBoxMap();
    }

    public void setCurrentUser(User user) { this.currentUser = user; }
    public User getCurrentUser() { return currentUser; }

    public void addUser(User user) { users.put(user.getUsername(), user); }
    public User getUser(String username) { return users.get(username); }
    public User removeUser(String username) { return users.remove(username); }
    public boolean userExists(String username) { return users.containsKey(username); }

    /**
     * Procesa la tecla ingresada y mueve al jugador en la direccion correspondiente.
     * @param input tecla ingresada (w, a, s, d o up, down, left, right)
     */
    public void handleInput(String input) {
        String direction = mapInputToDirection(input);
        if (direction != null) { game.movePlayer(direction); }
    }

    /**
     * Guarda el estado actual de la partida en disco.
     */
    public void saveState() {
        GameSaver.save(game);
    }

    private String mapInputToDirection(String input) {
        switch (input.toLowerCase()) {
            case "w": case "up":    return "UP";
            case "s": case "down":  return "DOWN";
            case "a": case "left":  return "LEFT";
            case "d": case "right": return "RIGHT";
            default: return null;
        }
    }

    /**
     * Actualiza el estado del juego verificando condicion de victoria.
     */
    public void update() { game.updateGame(); }

    /**
     * Reinicia el juego actual al estado inicial del nivel.
     */
    public void restart() { game.resetGame(); }

    public Game getGame() { return game; }
}