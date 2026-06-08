package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Level3ModeController {

    private Stage stage;
    private GameController gameController;

    /**
     * Inyecta dependencias desde VictoryController.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
    }

    /**
     * Carga el nivel 3 en modo manual — el jugador lo controla con WASD.
     */
    @FXML
    public void handleManual() {
        loadGameScreen(3);
    }

    /**
     * Carga el nivel 3 y lo resuelve automaticamente con BFS.
     */
    @FXML
    public void handleBFS() {
        loadAndSolve("BFS");
    }

    /**
     * Carga el nivel 3 y lo resuelve automaticamente con DFS.
     */
    @FXML
    public void handleDFS() {
        loadAndSolve("DFS");
    }

    /**
     * Navega de vuelta al menu principal.
     */
    @FXML
    public void handleBack() {
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

    /**
     * Carga el nivel 3 y llama al metodo de resolucion automatica indicado.
     * @param algorithm "BFS" o "DFS"
     */
    private void loadAndSolve(String algorithm) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/GameView.fxml"));
            Parent root = loader.load();

            GameScreenController controller = loader.getController();
            controller.init(stage, gameController, 3);

            stage.setScene(new Scene(root));
            stage.show();

            // Lanza el algoritmo automatico despues de que la escena este cargada
            if ("BFS".equals(algorithm)) {
                controller.handleSolveBFS();
            } else {
                controller.handleSolveDFS();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga la pantalla de juego en modo manual para el nivel indicado.
     * @param levelId ID del nivel a cargar
     */
    private void loadGameScreen(int levelId) {
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