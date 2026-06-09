import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {
    private Position pos1;
    private Position pos2;

    /**
     * setupStage13:
     * Dos posiciones: una igual (2,2) vs (2,2)
     * y otra diferente (2,2) vs (3,2).
     */
    private void setupStage13Equal() {
        pos1 = new Position(2, 2);
        pos2 = new Position(2, 2);
    }

    private void setupStage13Different() {
        pos1 = new Position(2, 2);
        pos2 = new Position(3, 2);
    }


    @Test
    public void isSamePositionPositiveTest() {
        // Arrange
        setupStage13Equal();

        // Act
        boolean result = pos1.isSamePosition(pos2);

        // Assert
        Assertions.assertTrue(result);
    }

    //Este test va a pasar positivo por el hecho de que el metodo original retorna un boolean y
    //no se puede dejar null un metodo que retorna boolean
    @Test
    public void isSamePositionDifferentTest() {
        // Arrange
        setupStage13Different();

        // Act
        boolean result = pos1.isSamePosition(pos2);

        // Assert
        Assertions.assertFalse(result);
    }
}
