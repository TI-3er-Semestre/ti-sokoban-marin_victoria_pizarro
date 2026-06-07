package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Level;
import model.LevelLoader;
import model.Position;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Sokoban cargando...");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(scene);
        primaryStage.show();
        Level level = LevelLoader.loadLevel(
                "src/main/resources/assets/levels/level1.json"
        );

        if (level != null) {
            System.out.println("Nivel: " + level.getId());

            Position p = level.getPlayerStart();

            System.out.println(
                    "Jugador en: " +
                            p.getX() + "," +
                            p.getY()
            );
            System.out.println(
                    "Cajas: " +
                            level.getBoard().getBoxes().getLength()
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}