package gui;

import control.GameController;
import control.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // La UI solo instancia al Controlador.
        GameController gameController = new GameController();

        // Cargar vista del menú principal
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/assets/views/MainMenuView.fxml"));
        Parent root = loader.load();

        // se mete el controlador en la vista
        MainMenuController menuController = loader.getController();
        menuController.init(primaryStage, gameController);

        // Buscamos el usuario a través del controlador usando String
        menuController.setPlayer(gameController.getUser(gameController.getDefaultUsername()));

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}