package gui;

import control.SokobanController;
import model.*;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Nivel 1: básico
        Board boardLevel1 = buildLevel1Board();
        Level level1 = new Level(1, boardLevel1);

        // Usuario por defecto
        User defaultUser = new User(
                "Player", "player@game.com", "player1",
                "default", Difficulty.EASY
        );

        // Modelo
        Game game = new Game(level1, defaultUser);

        // Controlador del modelo
        GameController gameController = new GameController(game);
        gameController.addUser(defaultUser);

        // Controlador MVC
        SokobanController sokobanController = new SokobanController(gameController);

        // Vista
        Menu menu = new Menu(sokobanController, primaryStage);
        menu.showMenu();
    }

    /**
     * Construye el tablero del Nivel 1: 1 caja, 1 objetivo, sin obstáculos.
     * @return Board configurado para el nivel 1
     */
    private Board buildLevel1Board() {
        int width = 7, height = 7;
        Board board = new Board(width, height);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        for (int x = 1; x <= 5; x++)
            for (int y = 1; y <= 5; y++)
                board.setCell(x, y, new Cell(CellType.FLOOR, false));

        board.setCell(3, 4, new Cell(CellType.GOAL, false));
        board.setCell(3, 3, new Cell(CellType.FLOOR, false));
        board.addBox(new Box(new Position(3, 3)));

        return board;
    }

    public static void main(String[] args) {
        launch(args);
    }
}