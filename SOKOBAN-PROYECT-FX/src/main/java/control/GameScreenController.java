package control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.stage.Stage;
import model.*;
import structures.CustomIterator;
import structures.LinkedList;
import structures.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import model.GameSaver;
import model.SavedState;

import java.io.IOException;

public class GameScreenController {

    @FXML private Label movesValueLabel;
    @FXML private Label pushesValueLabel;
    @FXML private Label levelValueLabel;
    @FXML private Label timeValueLabel;
    @FXML private Canvas gameCanvas;
    @FXML private StackPane gamePane;

    private static final int CELL_SIZE = 64;

    private Stage stage;
    private GameController gameController;
    private int currentLevelId;
    private GameTimer timer;
    private Timeline autoTimeline;

    private static final int ANIMATION_DELAY_MS = 400;

    private Image spriteWall;
    private Image spriteFloor;
    private Image spriteGoal;
    private Image spriteBox;
    private Image spriteBoxOnGoal;
    private Image spritePlayer;

    /**
     * Inyecta dependencias, carga el nivel y restaura estado guardado si existe.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     * @param levelId        ID del nivel a cargar
     */
    public void init(Stage stage, GameController gameController, int levelId) {
        this.stage = stage;
        this.gameController = gameController;
        this.currentLevelId = levelId;

        // Siempre carga el nivel fresco al entrar a la pantalla de juego
        gameController.loadFreshLevel(levelId);

        SavedState saved = GameSaver.load();
        if (saved != null && saved.getLevelId() == levelId) {
            gameController.restoreSavedState(saved);
            GameSaver.deleteSave();
        }

        levelValueLabel.setText(String.format("%02d", levelId));

        timer = new GameTimer(gameController.getGame().getStats(), timeValueLabel);
        timer.start();

        loadSprites();
        drawBoard();
        updateHUD();
        setupKeyboard();
    }

    /**
     * Carga los sprites desde la carpeta assets/sprites.
     */
    private void loadSprites() {
        spriteWall      = loadImage("/assets/sprites/block_05.png");
        spriteFloor     = loadImage("/assets/sprites/ground_03.png");
        spriteGoal      = loadImage("/assets/sprites/environment_11.png");
        spriteBox       = loadImage("/assets/sprites/crate_02.png");
        spriteBoxOnGoal = loadImage("/assets/sprites/crate_25.png");
        spritePlayer    = loadImage("/assets/sprites/player_05.png");
    }

    /**
     * Carga una imagen desde el classpath.
     * @param path ruta del recurso
     * @return Image cargada, o null si no se encuentra
     */
    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Detiene el cronometro de la partida.
     */
    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    /**
     * Configura el listener de teclado en la escena.
     */
    private void setupKeyboard() {
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();

        gameCanvas.sceneProperty().addListener(new ChangeListener<javafx.scene.Scene>() {
            @Override
            public void changed(ObservableValue<? extends javafx.scene.Scene> obs,
                                javafx.scene.Scene oldScene,
                                javafx.scene.Scene newScene) {
                if (newScene != null) {
                    newScene.addEventFilter(KeyEvent.KEY_PRESSED,
                            new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    handleKeyPressed(event);
                                }
                            });
                }
            }
        });
    }

    /**
     * Procesa los eventos de teclado del jugador.
     * @param event evento de tecla presionada
     */
    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP:    case W: move("UP");    break;
            case DOWN:  case S: move("DOWN");  break;
            case LEFT:  case A: move("LEFT");  break;
            case RIGHT: case D: move("RIGHT"); break;
            case Z:
                if (event.isControlDown()) handleUndo();
                break;
            case R:      handleRestart(); break;
            case ESCAPE: handleMenu();    break;
            default: break;
        }
    }

    /**
     * Mueve al jugador en la direccion indicada y actualiza la vista.
     * @param direction direccion del movimiento (UP, DOWN, LEFT, RIGHT)
     */
    private void move(String direction) {
        gameController.handleInput(direction);
        gameController.update();
        drawBoard();
        updateHUD();

        if (gameController.getGame().checkWin()) {
            stopTimer();
            gameController.getGame().saveGame();
            goToVictory();
        }
    }

    /**
     * Detiene el juego y navega al menu principal.
     */
    @FXML
    public void handleMenu() {
        stopTimer();
        stopAutoSolve();
        goToMenu();
    }

    /**
     * Deshace el ultimo movimiento del jugador.
     */
    @FXML
    public void handleUndo() {
        stopAutoSolve();
        gameController.getGame().undoMove();
        drawBoard();
        updateHUD();
    }

    /**
     * Reinicia el nivel actual desde el principio recargando el JSON.
     */
    @FXML
    public void handleRestart() {
        stopAutoSolve();
        stopTimer();
        gameController.loadFreshLevel(currentLevelId);
        timer = new GameTimer(gameController.getGame().getStats(), timeValueLabel);
        timer.start();
        drawBoard();
        updateHUD();
    }

    /**
     * Resuelve el nivel con BFS + Floyd-Warshall en hilo separado.
     */
    @FXML
    public void handleSolveBFS() {
        Game game = gameController.getGame();

        FloydWarshall fw = new FloydWarshall(game.getBoard());
        BFSAuto solver = new BFSAuto(game.getBoard(), fw);
        GameState inicial = solver.buildInitialState(game);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<String> solucion = solver.solve(inicial);
                javafx.application.Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (solucion != null) {
                            animateSolution(solucion);
                        }
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Resuelve el nivel con DFS + Dijkstra en hilo separado.
     */
    @FXML
    public void handleSolveDFS() {
        Game game = gameController.getGame();

        Dijkstra dij = new Dijkstra(game.getBoard());
        DFSAuto solver = new DFSAuto(game.getBoard(), dij);
        GameState inicial = solver.buildInitialState(game);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<String> solucion = solver.solve(inicial);

                javafx.application.Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (solucion != null) {
                            animateSolution(solucion);
                        }
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Alias de handleSolveBFS para compatibilidad con el FXML.
     */
    @FXML
    public void handleSolveBFSFloyd() {
        handleSolveBFS();
    }

    /**
     * Alias de handleSolveDFS para compatibilidad con el FXML.
     */
    @FXML
    public void handleSolveDFSFloyd() {
        handleSolveDFS();
    }

    /**
     * Anima la lista de movimientos de la solucion paso a paso.
     * @param solution lista de movimientos en orden de ejecucion
     */
    private void animateSolution(LinkedList<String> solution) {
        if (solution == null) return;

        Queue<String> moveQueue = new Queue<>();
        CustomIterator<String> it = solution.iterator();
        while (it.hasNext()) {
            moveQueue.enqueue(it.next().getData());
        }

        stopAutoSolve();
        autoTimeline = new Timeline();
        autoTimeline.setCycleCount(moveQueue.size());

        autoTimeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(ANIMATION_DELAY_MS),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String direction = moveQueue.dequeue();
                        if (direction != null) {
                            move(direction);
                        }
                    }
                }));

        autoTimeline.play();
    }

    /**
     * Detiene la animacion automatica del solver si esta en curso.
     */
    private void stopAutoSolve() {
        if (autoTimeline != null) {
            autoTimeline.stop();
            autoTimeline = null;
        }
    }

    /**
     * Dibuja el tablero completo en el canvas usando los sprites cargados.
     */
    private void drawBoard() {
        Game game = gameController.getGame();
        if (game == null || game.getBoard() == null || gameCanvas == null) return;

        Board board   = game.getBoard();
        Player player = game.getPlayer();
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell == null) continue;

                double px = x * CELL_SIZE;
                double py = y * CELL_SIZE;

                if (cell.getType() == CellType.WALL) {
                    drawSprite(gc, spriteWall, px, py);
                } else if (cell.isGoal()) {
                    drawSprite(gc, spriteFloor, px, py);
                    drawSprite(gc, spriteGoal, px, py);
                } else {
                    drawSprite(gc, spriteFloor, px, py);
                }

                if (cell.hasBox()) {
                    if (cell.isGoal()) {
                        drawSprite(gc, spriteBoxOnGoal, px, py);
                    } else {
                        drawSprite(gc, spriteBox, px, py);
                    }
                }

                if (player != null
                        && player.getPosition().getX() == x
                        && player.getPosition().getY() == y) {
                    drawSprite(gc, spritePlayer, px, py);
                }
            }
        }
    }

    /**
     * Dibuja un sprite en la posicion indicada del canvas.
     * Si el sprite es null dibuja un rectangulo gris como fallback.
     * @param gc     contexto grafico del canvas
     * @param sprite imagen a dibujar
     * @param px     coordenada X en pixeles
     * @param py     coordenada Y en pixeles
     */
    private void drawSprite(GraphicsContext gc, Image sprite, double px, double py) {
        if (sprite != null) {
            gc.drawImage(sprite, px, py, CELL_SIZE, CELL_SIZE);
        } else {
            gc.setFill(Color.GRAY);
            gc.fillRect(px, py, CELL_SIZE, CELL_SIZE);
        }
    }

    /**
     * Actualiza las etiquetas del HUD con las estadisticas actuales.
     */
    private synchronized void updateHUD() {
        GameStats stats = gameController.getGame().getStats();
        if (stats == null) return;

        movesValueLabel.setText(String.format("%03d", stats.getMoves()));
        pushesValueLabel.setText(String.format("%03d", stats.getPushes()));

        int totalSeconds = (int) stats.getTime();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        timeValueLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Navega a la pantalla de victoria con las estadisticas del nivel completado.
     */
    private void goToVictory() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/VictoryView.fxml"));
            Parent root = loader.load();

            VictoryController controller = loader.getController();
            controller.init(stage, gameController, currentLevelId,
                    (int) gameController.getGame().getStats().getTime());

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega de vuelta al menu principal.
     */
    private void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/MainMenuView.fxml"));
            Parent root = loader.load();

            MainMenuController controller = loader.getController();
            controller.init(stage, gameController);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}