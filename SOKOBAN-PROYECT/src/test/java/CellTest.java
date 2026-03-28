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

    @Test
    public void testIsWalkable_Wall() {

        // Arrange
        setupStage9();

        // Act
        boolean result = wallCell.isWalkable();

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsGoal_True() {

        // Arrange
        setupStage9();

        // Act
        boolean result = goalCell.isGoal();

        // Assert
        Assertions.assertTrue(result);
    }

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