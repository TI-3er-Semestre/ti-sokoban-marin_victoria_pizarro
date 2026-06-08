package control;

import model.*;
import structures.HashTable;


public class GameController {

    private Game game;
    private HashTable<String, User> users;


    public GameController() {
        this.users = new HashTable<>(10);
        initializeGame();
    }
    private void initializeGame() {
        // El controlador carga los niveles del modelo
        Level level1 = LevelLoader.loadLevel("/assets/levels/level1.json");
        Level level2 = LevelLoader.loadLevel("/assets/levels/level2.json");
        Level level3 = LevelLoader.loadLevel("/assets/levels/level3.json");

        // El controlador crea el usuario por defecto del modelo
        User defaultUser = new User(
                "Player", "player@game.com", "player1",
                "player_05.png", Difficulty.EASY
        );

        // El controlador inicializa la instancia principal de Game
        this.game = new Game(level1, defaultUser);
        this.game.addLevel(level2);
        this.game.addLevel(level3);

        // Registra al usuario en la tabla hash
        addUser(defaultUser);
    }


    public String getDefaultUsername() {
        return "player1";
    }

    /**
     * Registra un usuario en la tabla hash usando su username como clave.
     * @param user usuario a registrar
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Busca y retorna el usuario con el username dado.
     * @param username nombre de usuario a buscar
     * @return el usuario encontrado, o null si no existe
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Elimina el usuario con el username dado de la tabla hash.
     * @param username nombre de usuario a eliminar
     * @return el usuario eliminado, o null si no existia
     */
    public User removeUser(String username) {
        return users.remove(username);
    }

    /**
     * Indica si un usuario con el username dado esta registrado.
     * @param username nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Procesa la tecla ingresada y mueve al jugador.
     * @param input tecla ingresada (w, a, s, d o flechas)
     */
    public void handleInput(String input) {
        String direction = mapInputToDirection(input);
        if (direction != null) {
            game.movePlayer(direction);
        }
    }

    /**
     * Convierte la tecla del usuario en una direccion del juego.
     * @param input tecla ingresada
     * @return direccion equivalente o null si es invalida
     */
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
     * Actualiza el estado del juego.
     */
    public void update() {
        game.updateGame();
    }

    /**
     * Inicia un nuevo juego con el nivel y usuario dados.
     * @param level nivel a cargar
     * @param user  usuario que jugara
     */
    public void start(Level level, User user) {
        game = new Game(level, user);
        game.startGame();
    }

    /**
     * Reinicia el juego actual.
     */
    public void restart() {
        game.resetGame();
    }

    /**
     * Retorna el juego actualmente controlado.
     * @return instancia de Game en curso
     */
    public Game getGame() {
        return game;
    }
}