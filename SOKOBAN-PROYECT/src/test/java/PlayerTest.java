import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player player;

    /**
     * setupStage12:
     * Jugador inicializado en una posición conocida (2,2).
     */
    private void setupStage12() {
        Position initialPosition = new Position(2, 2);
        player = new Player(initialPosition);
    }

    //Test para verificar que la posición esta siendo tomada correctamente
    //Estos test de esta clase van a pasar debido a que los getters/setters ya están implementados en el esqueleto
    // Simplemente es para validar que el metodo si funciona correctamente
    @Test
    public void getPositionTest() {
        // Arrange
        setupStage12();

        // Act
        Position result = player.getPosition();

        // Assert
        Assertions.assertEquals(2, result.getX());
        Assertions.assertEquals(2, result.getY());
    }

    //Test para validar que se está cambiando correctamente la posición del jugador
    @Test
    public void setPositionTest() {
        // Arrange
        setupStage12();
        Position newPosition = new Position(3, 2);

        // Act
        player.setPosition(newPosition);

        // Assert
        Position result = player.getPosition();
        Assertions.assertEquals(3, result.getX());
        Assertions.assertEquals(2, result.getY());
    }
}
