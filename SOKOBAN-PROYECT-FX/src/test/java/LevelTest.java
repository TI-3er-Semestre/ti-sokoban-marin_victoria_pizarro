import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LevelTest {

    private Level level;

    /**
     * setupStage10:
     * Nivel con al menos una caja fuera de su objetivo correspondiente.
     */
    private void setupStage10() {
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

        // Un objetivo con caja
        board.setCell(2, 2, new Cell(CellType.GOAL, true));

        // Una caja fuera de objetivo
        board.setCell(3, 2, new Cell(CellType.FLOOR, true));

        level = new Level(1, board, new Position(1,1));
    }

    /**
     * setupStage11:
     * Nivel donde todas las cajas coinciden con las posiciones objetivo.
     */
    private void setupStage11() {
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

        // Todas las cajas están en objetivos
        board.setCell(2, 2, new Cell(CellType.GOAL, true));
        board.setCell(3, 2, new Cell(CellType.GOAL, true));

        level = new Level(1, board, new Position(1,1));
    }

    //Test para verificar desde la clase Level que el nivel no está completo
    //Porque hay una caja fuera de objetivo
    /*
     * Este test va a pasar debido a que el metodo debe retornar boolean y
     * por defecto debe retornar algo
     */
    @Test
    public void isCompletedNegativeTest() {
        // Arrange
        setupStage10();

        // Act
        boolean result = level.isCompleted();

        // Assert
        Assertions.assertFalse(result);
    }

    //Test para validar desde la clase test de que el nivel está completado
    @Test
    public void isCompletedPositiveTest() {
        // Arrange
        setupStage11();

        // Act
        boolean result = level.isCompleted();

        // Assert
        Assertions.assertTrue(result);
    }
}
