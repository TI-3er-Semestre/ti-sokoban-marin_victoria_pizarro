import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;

    /**
     * setupStage8:
     * Tablero con diferentes tipos de celdas:
     * (2,2) celda libre, (0,0) muro y (3,2) celda con caja.
     */
    private void setupStage8() {
        // Arrange
        board = new Board(5,5);

        board.setCell(2,2, new Cell(CellType.FLOOR,false));
        board.setCell(0,0, new Cell(CellType.WALL,false));
        board.setCell(3,2, new Cell(CellType.FLOOR,true));
    }

    /**
     * Verifica que isFree retorne true
     * cuando la celda está libre.
     */
    @Test
    public void testIsFree_FreeCell() {

        // Arrange
        setupStage8();

        // Act
        boolean result = board.isFree(2,2);

        // Assert
        Assertions.assertTrue(result);
    }

    /**
     * Verifica que isFree retorne false
     * cuando la celda es un muro.
     */
    @Test
    public void testIsFree_Wall() {

        // Arrange
        setupStage8();

        // Act
        boolean result = board.isFree(0,0);

        // Assert
        Assertions.assertFalse(result);
    }
    /**
     * Verifica que isFree retorne false
     * cuando la celda contiene una caja.
     */
    @Test
    public void testIsFree_CellWithBox() {

        // Arrange
        setupStage8();

        // Act
        boolean result = board.isFree(3,2);

        // Assert
        Assertions.assertFalse(result);
    }
}