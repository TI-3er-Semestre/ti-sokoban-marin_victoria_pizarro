package model;

import structures.HashTable;

public class GameController {
    private Game game;
    private HashTable<String, User> users;


    public GameController(Game game) {

        this.game = game;
        this.users = new HashTable<>(10);
    }

    /**
     * Registra un usuario en la tabla hash usando su username como clave.
     * Si el username ya existe, reemplaza al usuario anterior.
     *
     * @param user usuario a registrar; su username no debe ser nulo
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * Busca y retorna el usuario con el username dado.
     * Usa la tabla hash para acceso en tiempo O(1).
     *
     * @param username nombre de usuario a buscar
     * @return el usuario encontrado, o null si no existe
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Elimina el usuario con el username dado de la tabla hash.
     *
     * @param username nombre de usuario a eliminar
     * @return el usuario eliminado, o null si no existía
     */
    public User removeUser(String username) {
        return users.remove(username);
    }

    /**
     * Indica si un usuario con el username dado está registrado.
     *
     * @param username nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Maneja la entrada del usuario.
     *
     * @param input tecla ingresada (w,a,s,d)
     */
    public void handleInput(String input) {
        String direction = mapInputToDirection(input);

        if (direction != null) {
            game.movePlayer(direction);
        }
    }

    /**
     * Convierte las teclas del usuario en direcciones del juego.
     *
     * @param input tecla ingresada
     * @return dirección equivalente o null si es inválida
     */
    private String mapInputToDirection(String input) {
        switch (input.toLowerCase()) {
            case "w":
                return "UP";
            case "s":
                return "DOWN";
            case "a":
                return "LEFT";
            case "d":
                return "RIGHT";
            default:
                return null;
        }
    }

    /**
     * Actualiza el juego.
     */
    public void update() {
        game.updateGame();
    }

    /**
     * Inicia un nuevo juego.
     *
     * @param level nivel
     * @param user  usuario
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
     *
     * @return instancia de Game en curso
     */
    public Game getGame() {
        return game;
    }
}