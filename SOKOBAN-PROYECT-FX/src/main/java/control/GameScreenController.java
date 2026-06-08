package control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.IOException;


public class GameScreenController {

    @FXML private Label movesValueLabel;
    @FXML private Label pushesValueLabel;
    @FXML private Label levelValueLabel;
    @FXML private Label timeValueLabel;
    @FXML private Canvas gameCanvas;
    @FXML private StackPane gamePane;

    private static final int CELL_SIZE = 64;

    private static final Color COLOR_WALL        = Color.web("#c0392b");
    private static final Color COLOR_FLOOR       = Color.web("#8a9ea8");
    private static final Color COLOR_GOAL        = Color.web("#8a9ea8");
    private static final Color COLOR_GOAL_BORDER = Color.web("#e67e22");
    private static final Color COLOR_BOX         = Color.web("#c8862a");
    private static final Color COLOR_BOX_GOAL    = Color.web("#2ecc71");
    private static final Color COLOR_PLAYER      = Color.web("#4a90d9");

    private Stage stage;
    private GameController gameController;
    private int currentLevelId;
    private GameTimer timer;
    private Timeline hudUpdater;



    private Timeline autoTimeline;
    private static final int ANIMATION_DELAY_MS = 400;

    /**
     * Inyecta dependencias y carga el nivel indicado.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     * @param levelId        ID del nivel a cargar
     */
    public void init(Stage stage, GameController gameController, int levelId) {
        this.stage = stage;
        this.gameController = gameController;
        this.currentLevelId = levelId;

        gameController.getGame().loadLevel(levelId);

        levelValueLabel.setText(String.format("%02d", levelId));

        timer = new GameTimer(
                gameController.getGame().getStats()
        );

        timer.start();

        drawBoard();
        updateHUD();
        setupKeyboard();
        hudUpdater = new Timeline((
                new KeyFrame(Duration.seconds(1), e -> updateHUD())
        ));

        hudUpdater.setCycleCount(Timeline.INDEFINITE);
        hudUpdater.play();
    }

    /**
     * Inicia el cronometro de la partida.
     */



    /**
     * Detiene el cronometro de la partida.
     */
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
        if(hudUpdater !=null){
            hudUpdater.stop();
        }
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
     * @param direction direccion del movimiento
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
     * Maneja el boton de menu: detiene el juego y navega al menu principal.
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
     * Reinicia el nivel actual desde el principio.
     */
    @FXML
    public void handleRestart() {

        stopAutoSolve();
        stopTimer();

        gameController.getGame().loadLevel(currentLevelId);

        timer = new GameTimer(
                gameController.getGame().getStats()
        );

        timer.start();
        drawBoard();
        updateHUD();
    }


    /**
     * Resuelve el nivel con BFS y anima la solucion.
     */
    @FXML
    public void handleSolveBFS() {
        Game game = gameController.getGame();
        BFSAuto solver = new BFSAuto(game.getBoard());
        animateSolution(solver.solve(solver.buildInitialState(game)));
    }

    /**
     * Resuelve el nivel con BFS + poda Floyd-Warshall y anima la solucion.
     */
    @FXML
    public void handleSolveBFSFloyd() {
        Game game = gameController.getGame();
        FloydWarshall fw = new FloydWarshall(game.getBoard());
        BFSAuto solver = new BFSAuto(game.getBoard(), fw);
        animateSolution(solver.solve(solver.buildInitialState(game)));
    }

    /**
     * Resuelve el nivel con DFS y anima la solucion.
     */
    @FXML
    public void handleSolveDFS() {
        Game game = gameController.getGame();
        DFSAuto solver = new DFSAuto(game.getBoard());
        animateSolution(solver.solve(solver.buildInitialState(game)));
    }

    /**
     * Resuelve el nivel con DFS + poda Floyd-Warshall y anima la solucion.
     */
    @FXML
    public void handleSolveDFSFloyd() {
        Game game = gameController.getGame();
        FloydWarshall fw = new FloydWarshall(game.getBoard());
        DFSAuto solver = new DFSAuto(game.getBoard(), fw);
        animateSolution(solver.solve(solver.buildInitialState(game)));
    }

    /**
     * Anima la lista de movimientos paso a paso con Timeline de JavaFX.
     * @param solution lista de movimientos, o null si no hay solucion
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

        EventHandler<ActionEvent> stepHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String direction = moveQueue.dequeue();
                if (direction != null) {
                    move(direction);
                }
            }
        };

        autoTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(ANIMATION_DELAY_MS), stepHandler));
        autoTimeline.play();
    }

    /**
     * Detiene la animacion automatica en curso.
     */
    private void stopAutoSolve() {
        if (autoTimeline != null) {
            autoTimeline.stop();
            autoTimeline = null;
        }
    }

    /**
     * Dibuja el tablero completo en el canvas usando el estado actual del modelo.
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
                    gc.setFill(COLOR_WALL);
                    gc.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    gc.setStroke(Color.web("#96281b"));
                    gc.setLineWidth(2);
                    gc.strokeRect(px, py, CELL_SIZE, CELL_SIZE);
                } else if (cell.isGoal()) {
                    gc.setFill(COLOR_GOAL);
                    gc.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    gc.setStroke(COLOR_GOAL_BORDER);
                    gc.setLineWidth(3);
                    gc.strokeRect(px + 2, py + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                } else {
                    gc.setFill(COLOR_FLOOR);
                    gc.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    gc.setStroke(Color.web("#6a7e88"));
                    gc.setLineWidth(1);
                    gc.strokeRect(px, py, CELL_SIZE, CELL_SIZE);
                }

                if (cell.hasBox()) {
                    if (cell.isGoal()) {
                        gc.setFill(COLOR_BOX_GOAL);
                    } else {
                        gc.setFill(COLOR_BOX);
                    }
                    gc.fillRoundRect(px + 4, py + 4, CELL_SIZE - 8, CELL_SIZE - 8, 8, 8);
                    gc.setStroke(cell.isGoal()
                            ? Color.web("#1a9e55") : Color.web("#a06020"));
                    gc.setLineWidth(2);
                    gc.strokeRoundRect(px + 4, py + 4, CELL_SIZE - 8, CELL_SIZE - 8, 8, 8);
                }

                if (player != null
                        && player.getPosition().getX() == x
                        && player.getPosition().getY() == y) {
                    gc.setFill(COLOR_PLAYER);
                    gc.fillOval(px + 8, py + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                    gc.setStroke(Color.web("#1a5fa0"));
                    gc.setLineWidth(2);
                    gc.strokeOval(px + 8, py + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                }
            }
        }
    }

    /**
     * Actualiza las etiquetas del HUD con las estadisticas actuales.
     */
    private void updateHUD() {
        GameStats stats = gameController.getGame().getStats();
        if (stats == null) return;

        movesValueLabel.setText(String.format("%03d", stats.getMoves()));
        pushesValueLabel.setText(String.format("%03d", stats.getPushes()));
        int totalSeconds =
                (int) stats.getTime();

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        timeValueLabel.setText(
                String.format("%02d:%02d", minutes, seconds)
        );
    }

    /**
     * Navega a la pantalla de victoria.
     */
    private void goToVictory() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/VictoryView.fxml"));
            Parent root = loader.load();

            VictoryController controller = loader.getController();
            controller.init(stage, gameController, currentLevelId, (int) gameController.getGame().getStats().getTime());


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
                    getClass().getResource("/view/MainMenuView.fxml"));
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