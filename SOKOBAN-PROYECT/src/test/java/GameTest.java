import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;

    /**
     * setupStage6:
     * Todas las cajas están sobre objetivos.
     */
    private void setupStage6() {
        Board board = new Board(5, 5);

        // Bordes WALL, interior FLOOR
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (x == 0 || x == 4 || y == 0 || y == 4) {
                    board.setCell(x, y, new Cell(CellType.WALL, false));
                } else {
                    board.setCell(x, y, new Cell(CellType.FLOOR, false));
                }
            }
        }

        // Objetivos con cajas (nivel COMPLETO)
        board.setCell(2, 2, new Cell(CellType.GOAL, true));
        board.setCell(3, 2, new Cell(CellType.GOAL, true));

        Level level = new Level(1, board);
        User user = new User("Juan", "juan@mail.com", "juan123", "avatar.png", Difficulty.EASY);

        game = new Game(level, user);
    }

    /**
     * setupStage7:
     * Al menos una caja NO está sobre objetivo.
     */
    private void setupStage7() {
        Board board = new Board(5, 5);

        // Bordes WALL, interior FLOOR
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (x == 0 || x == 4 || y == 0 || y == 4) {
                    board.setCell(x, y, new Cell(CellType.WALL, false));
                } else {
                    board.setCell(x, y, new Cell(CellType.FLOOR, false));
                }
            }
        }

        // Un goal con caja (correcto)
        board.setCell(2, 2, new Cell(CellType.GOAL, true));

        // Una caja fuera de objetivo
        board.setCell(3, 2, new Cell(CellType.FLOOR, true));

        Level level = new Level(1, board);
        User user = new User("Juan", "juan@mail.com", "juan123", "avatar.png", Difficulty.EASY);

        game = new Game(level, user);
    }

    //Test con todas las cajas en objetivos
    @Test
    public void checkWinAllObjectivesTest() {
        // Arrange
        setupStage6();

        // Act
        boolean result = game.checkWin();

        // Assert
        Assertions.assertTrue(result);
    }

    /*
    * Test con una caja fuera de objetivo
    * Este test va a pasar debido a que el metodo checkWin() debe retornar boolean y
    * por defecto debe retornar algo
    * */
    @Test
    public void checkWinNoObjetivesTest() {
        // Arrange
        setupStage7();

        // Act
        boolean result = game.checkWin();

        // Assert
        Assertions.assertFalse(result);
    }

    //Test en caso de que se haya finalizado correctamente la partida y
    // cambia su estado a finalizado
    @Test
    public void updateGameFinishedTest() {
        // Arrange
        setupStage6();

        // Act
        game.updateGame();

        // Assert
        Assertions.assertTrue(game.isFinished());
    }
    // Test en caso de que no se compruebe la victoria y el juego continua sin finalizar
    /*
    * Este test va a pasar debido a que el metodo checkWin() debe retornar boolean y
    * por defecto debe retornar algo
    */
    @Test
    public void updateGameNotFinishedTest() {
        // Arrange
        setupStage7();

        // Act
        game.updateGame();

        // Assert
        Assertions.assertFalse(game.isFinished());
    }
}


