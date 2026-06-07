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

        // Nivel 2: avanzado
        Board boardLevel2 = buildLevel2Board();
        Level level2 = new Level(2, boardLevel2);

        // Usuario por defecto
        User defaultUser = new User(
                "Player", "player@game.com", "player1",
                "default", Difficulty.EASY
        );

        // Modelo
        Game game = new Game(level1, defaultUser);
        game.addLevel(level2);

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

    /**
     * Construye el tablero del Nivel 2: 3 cajas, 3 objetivos, con obstáculos internos.
     * @return Board configurado para el nivel 2
     */
    private Board buildLevel2Board() {
        int width = 9, height = 9;
        Board board = new Board(width, height);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        for (int x = 1; x <= 7; x++)
            for (int y = 1; y <= 7; y++)
                board.setCell(x, y, new Cell(CellType.FLOOR, false));

        board.setCell(4, 2, new Cell(CellType.WALL, false));
        board.setCell(4, 4, new Cell(CellType.WALL, false));

        board.setCell(2, 4, new Cell(CellType.GOAL, false));
        board.setCell(6, 4, new Cell(CellType.GOAL, false));
        board.setCell(4, 6, new Cell(CellType.GOAL, false));

        board.addBox(new Box(new Position(2, 2)));
        board.addBox(new Box(new Position(6, 2)));
        board.addBox(new Box(new Position(4, 5)));

        return board;
    }

    public static void main(String[] args) {
        launch(args);
    }
}