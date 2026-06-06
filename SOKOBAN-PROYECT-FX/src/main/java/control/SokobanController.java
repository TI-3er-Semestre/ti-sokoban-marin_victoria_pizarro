package control;

import model.*;

/**
 * Controlador del patrón MVC. Recibe entradas de la Vista, las traduce
 * a acciones del Modelo y notifica a la Vista mediante callbacks.
 */
public class SokobanController {

    private GameController gameController;

    /** Callback que la Vista registra para redibujar el tablero. */
    private Runnable onBoardChanged;

    /** Callback que la Vista registra para mostrar la pantalla de victoria. */
    private Runnable onGameWon;

    /** Callback que la Vista registra cuando no se encuentra solución automática. */
    private Runnable onNoSolutionFound;

    /**
     * Crea el controlador MVC.
     * @param gameController controlador del modelo ya configurado
     */
    public SokobanController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Registra el callback que se ejecuta cuando el tablero cambia.
     * @param callback acción que la Vista ejecutará al recibir la notificación
     */
    public void setOnBoardChanged(Runnable callback) {
        this.onBoardChanged = callback;
    }

    /**
     * Registra el callback que se ejecuta cuando el jugador gana.
     * @param callback acción que la Vista ejecutará al recibir la notificación
     */
    public void setOnGameWon(Runnable callback) {
        this.onGameWon = callback;
    }

    /**
     * Registra el callback que se ejecuta cuando no hay solución automática.
     * @param callback acción que la Vista ejecutará al recibir la notificación
     */
    public void setOnNoSolutionFound(Runnable callback) {
        this.onNoSolutionFound = callback;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param user usuario a registrar
     */
    public void registerUser(User user) {
        gameController.addUser(user);
        user.register();
    }

    /**
     * Inicia una partida nueva con el nivel y usuario dados.
     * @param level nivel a cargar
     * @param user  usuario que jugará
     */
    public void startGame(Level level, User user) {
        gameController.start(level, user);
        notifyBoardChanged();
    }

    /**
     * Invoca el callback de tablero cambiado si fue registrado.
     */
    private void notifyBoardChanged() {
        if (onBoardChanged != null) onBoardChanged.run();
    }

    /**
     * Verifica si el nivel fue completado y notifica a la Vista.
     */
    private void checkAndNotifyWin() {
        if (gameController.getGame().checkWin()) {
            gameController.getGame().saveGame();
            if (onGameWon != null) onGameWon.run();
        }
    }

    /**
     * Retorna el Game actual para lectura desde la Vista.
     * @return instancia de Game en curso
     */
    public Game getGame() {
        return gameController.getGame();
    }

    /**
     * Retorna el tablero del nivel actual.
     * @return Board del nivel en curso
     */
    public Board getBoard() {
        return gameController.getGame().getBoard();
    }

    /**
     * Retorna el jugador del nivel actual.
     * @return Player en curso
     */
    public Player getPlayer() {
        return gameController.getGame().getPlayer();
    }

    /**
     * Retorna las estadísticas actuales de la partida.
     * @return GameStats con movimientos, empujes y tiempo
     */
    public GameStats getStats() {
        return gameController.getGame().getStats();
    }

    /**
     * Retorna el GameController subyacente.
     * @return GameController del modelo
     */
    public GameController getGameController() {
        return gameController;
    }
}