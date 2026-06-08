package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;


public class MainMenuController {

    @FXML private ToggleButton level1Btn;
    @FXML private ToggleButton level2Btn;
    @FXML private ToggleButton level3Btn;
    @FXML private Label playerNameLabel;
    @FXML private ToggleGroup levelGroup;

    private Stage stage;
    private GameController gameController;

    /**
     * Inyecta las dependencias necesarias desde MainApp.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        if (gameController.getCurrentUser() != null) {
            setPlayer(gameController.getCurrentUser());
        }
    }

    /**
     * Actualiza la etiqueta con el nombre del jugador registrado.
     * @param user usuario registrado
     */
    public void setPlayer(User user) {
        if (user != null) {
            playerNameLabel.setText("Jugador: " + user.getUsername());
        }
    }

    /**
     * Navega a la pantalla de juego con el nivel seleccionado.
     */
    @FXML
    public void handlePlay() {
        Toggle selected = levelGroup.getSelectedToggle();
        int levelId = 1;

        if (selected != null) {
            levelId = Integer.parseInt(selected.getUserData().toString());
        }

        loadGameScreen(levelId);
    }

    /**
     * Navega a la pantalla de registro.
     */
    @FXML
    public void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/RegisterView.fxml"));
            Parent root = loader.load();

            RegisterController controller = loader.getController();
            controller.init(stage, gameController);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla de estadisticas.
     */
    @FXML
    public void handleRanking() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/StatsView.fxml"));
            Parent root = loader.load();

            StatsController controller = loader.getController();
            controller.init(stage, gameController);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega a la pantalla del manual de usuario.
     */
    @FXML
    public void handleManual() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/ManualView.fxml"));
            Parent root = loader.load();

            ManualController controller = loader.getController();
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
    public void loadGameScreen(int levelId) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/GameView.fxml"));
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