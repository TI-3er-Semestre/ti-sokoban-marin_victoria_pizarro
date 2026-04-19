package model;

public class GameController {
    private Game game;


    public GameController(Game game) {

        this.game = game;
    }

    /**
     * Maneja la entrada del usuario.
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
     * @param level nivel
     * @param user usuario
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

}