import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStatsTest {
    private GameStats stats;

    /**
     * setupStage14:
     * Estadísticas inicializadas en cero
     * (movimientos y empujes).
     */
    private void setupStage14() {
        stats = new GameStats(1);
    }

    @Test
    public void addMoveTest() {
        // Arrange
        setupStage14();

        // Act
        stats.addMove();

        // Assert
        Assertions.assertEquals(1, stats.getMoves());
    }

    @Test
    public void addPushTest() {
        // Arrange
        setupStage14();

        // Act
        stats.addPush();

        // Assert
        Assertions.assertEquals(1, stats.getPushes());
    }

    @Test
    public void resetTest() {
        // Arrange
        setupStage14();
        stats.addMove();
        stats.addMove();
        stats.addPush();

        // Act
        stats.reset();

        // Assert
        Assertions.assertEquals(0, stats.getMoves());
        Assertions.assertEquals(0, stats.getPushes());
    }
}
