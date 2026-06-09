package control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Level3ModeController {

    private Stage stage;
    private GameController gameController;

    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
    }

    @FXML
    public void handleManual() {
        loadGameScreen(3);
    }

    @FXML
    public void handleBFS() {
        loadAndSolve("BFS");
    }

    @FXML
    public void handleDFS() {
        loadAndSolve("DFS");
    }

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
     * Carga GameView y luego lanza el solver via Platform.runLater.
     * NO llama loadFreshLevel aqui — lo hace GameScreenController.init()
     */
    private void loadAndSolve(String algorithm) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/GameView.fxml"));
            Parent root = loader.load();

            GameScreenController controller = loader.getController();
            // init() se encarga de loadFreshLevel internamente
            controller.init(stage, gameController, 3);

            stage.setScene(new Scene(root));
            stage.show();

            // runLater garantiza que init() ya terminó y la escena está visible
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if ("BFS".equals(algorithm)) {
                        controller.handleSolveBFS();
                    } else {
                        controller.handleSolveDFS();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga GameView en modo manual.
     * NO llama loadFreshLevel — lo hace GameScreenController.init()
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