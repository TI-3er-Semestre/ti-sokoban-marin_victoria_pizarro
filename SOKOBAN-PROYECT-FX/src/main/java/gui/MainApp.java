package gui;

import control.GameController;
import control.RegisterController;
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

        // Cargar vista de registro como pantalla inicial
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/assets/views/RegisterView.fxml"));
        Parent root = loader.load();

        RegisterController registerController = loader.getController();
        registerController.init(primaryStage, gameController);

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}