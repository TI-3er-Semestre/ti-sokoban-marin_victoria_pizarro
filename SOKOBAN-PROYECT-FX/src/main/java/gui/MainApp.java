package gui;

import control.GameController;
import control.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Cargar niveles desde archivos JSON
        Level level1 = LevelLoader.loadLevel("/assets/levels/level1.json");
        Level level2 = LevelLoader.loadLevel("/assets/levels/level2.json");
        Level level3 = LevelLoader.loadLevel("/assets/levels/level3.json");

        // Usuario por defecto
        User defaultUser = new User(
                "Player", "player@game.com", "player1",
                "player_05.png", Difficulty.EASY
        );

        // Modelo: Game arranca con nivel 1
        Game game = new Game(level1, defaultUser);
        game.addLevel(level2);
        game.addLevel(level3);

        // Controlador del modelo
        GameController gameController = new GameController(game);
        gameController.addUser(defaultUser);

        // Cargar vista del menu principal
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/assets/views/MainMenuView.fxml"));
        Parent root = loader.load();

        MainMenuController menuController = loader.getController();
        menuController.init(primaryStage, gameController);
        menuController.setPlayer(defaultUser);

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}