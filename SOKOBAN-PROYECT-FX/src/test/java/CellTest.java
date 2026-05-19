import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {

    private Cell floorCell;
    private Cell wallCell;
    private Cell goalCell;

    /**
     * setupStage9:
     * Celdas individuales de tipo FLOOR, GOAL y WALL,
     * sin presencia de cajas
     */
    private void setupStage9() {
        // Arrange
        floorCell = new Cell(CellType.FLOOR,false);
        wallCell = new Cell(CellType.WALL,false);
        goalCell = new Cell(CellType.GOAL,false);
    }

    /**
     * Verifica que las celdas tipo FLOOR y GOAL
     * sean consideradas caminables.
     */
    @Test
    public void testIsWalkable_FloorAndGoal() {

        // Arrange
        setupStage9();

        // Act
        boolean floorResult = floorCell.isWalkable();
        boolean goalResult = goalCell.isWalkable();

        // Assert
        Assertions.assertTrue(floorResult);
        Assertions.assertTrue(goalResult);
    }

    /**
     * Verifica que las celdas tipo WALL
     * NO sean caminables.
     */
    @Test
    public void testIsWalkable_Wall() {

        // Arrange
        setupStage9();

        // Act
        boolean result = wallCell.isWalkable();

        // Assert
        Assertions.assertFalse(result);
    }

    /**
     * Verifica que isGoal retorne true
     * cuando la celda es de tipo GOAL.
     */
    @Test
    public void testIsGoal_True() {

        // Arrange
        setupStage9();

        // Act
        boolean result = goalCell.isGoal();

        // Assert
        Assertions.assertTrue(result);
    }

    /**
     * Verifica que isGoal retorne false
     * cuando la celda NO es de tipo GOAL.
     */
    @Test
    public void testIsGoal_False() {

        // Arrange
        setupStage9();

        // Act
        boolean result = floorCell.isGoal();

        // Assert
        Assertions.assertFalse(result);
    }
}