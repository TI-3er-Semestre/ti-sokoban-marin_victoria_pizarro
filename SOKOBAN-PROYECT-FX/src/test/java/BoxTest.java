import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoxTest {
    private Box box;

    /**
     * setupStage3:
     * Caja inicializada en la posición (3,2).
     */
    private void setupStage3() {
        Position initialPosition = new Position(3, 2);
        box = new Box(initialPosition);
    }

    @Test
    public void getPositionTest() {
        // Arrange
        setupStage3();

        // Act
        Position result = box.getPosition();

        // Assert
        Assertions.assertTrue(result.isSamePosition(new Position(3, 2)));
    }

    @Test
    public void setPositionTest() {
        // Arrange
        setupStage3();
        Position newPosition = new Position(4, 2);

        // Act
        box.setPosition(newPosition);

        // Assert
        Position result = box.getPosition();
        Assertions.assertTrue(result.isSamePosition(new Position(4, 2)));
    }
}
