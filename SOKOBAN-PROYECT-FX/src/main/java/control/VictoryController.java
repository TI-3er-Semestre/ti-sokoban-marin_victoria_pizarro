package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.GameStats;

import java.io.IOException;

/**
 * Controlador de la pantalla de victoria.
 */
public class VictoryController {

    @FXML private Label victoryTitleLabel;
    @FXML private Label levelSubtitleLabel;
    @FXML private Label movesResultLabel;
    @FXML private Label pushesResultLabel;
    @FXML private Label timeResultLabel;
    @FXML private Button nextLevelButton;

    private static final String[] LEVEL_NAMES = {"", "BASICO", "AVANZADO", "EXPERTO"};

    private Stage stage;
    private GameController gameController;
    private int currentLevelId;

    /**
     * Inyecta dependencias y muestra las estadisticas de la partida.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     * @param levelId        ID del nivel completado
     * @param secondsElapsed tiempo total de la partida en segundos
     */
    public void init(Stage stage, GameController gameController,
                     int levelId, int secondsElapsed) {
        this.stage = stage;
        this.gameController = gameController;
        this.currentLevelId = levelId;

        levelSubtitleLabel.setText(
                "NIVEL " + levelId + " - " + LEVEL_NAMES[levelId]);

        GameStats stats = gameController.getGame().getStats();
        if (stats != null) {
            movesResultLabel.setText(String.format("%03d", stats.getMoves()));
            pushesResultLabel.setText(String.format("%03d", stats.getPushes()));
        }

        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timeResultLabel.setText(String.format("%02d:%02d", minutes, seconds));

        if (levelId >= 3) {
            nextLevelButton.setVisible(false);
            nextLevelButton.setManaged(false);
        }
    }

    /**
     * Navega al siguiente nivel.
     */
    @FXML
    public void handleNextLevel() {
        loadGameScreen(currentLevelId + 1);
    }

    /**
     * Reinicia el nivel actual.
     */
    @FXML
    public void handleRetry() {
        loadGameScreen(currentLevelId);
    }

    /**
     * Navega de vuelta al menu principal.
     */
    @FXML
    public void handleMenu() {
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

    /**
     * Carga la pantalla de juego para el nivel indicado.
     * @param levelId ID del nivel a cargar
     */
    private void loadGameScreen(int levelId) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/GameView.fxml"));
            Parent root = loader.load();

            GameScreenController controller = loader.getController();
            controller.init(stage, gameController, levelId);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}