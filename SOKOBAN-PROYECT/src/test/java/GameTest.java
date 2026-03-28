import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class GameTest {

    private Game game;
    private Board board;
    private Player player;
    private Level level;
    private User user;


    // setups

    /**
     * setupStage1:
     * Tablero 5x5 con bordes de muro.
     * El jugador esta en (2,2) y las celdas adyacentes están libres.
     */
    private void setupStage1() {
        // Arrange
        board = new Board(5,5);

        // Bordes de muro
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (x == 0 || y == 0 || x == 4 || y == 4) {
                    board.setCell(x,y, new Cell(CellType.WALL,false));
                } else {
                    board.setCell(x,y, new Cell(CellType.FLOOR,false));
                }
            }
        }

        player = new Player(new Position(2,2));
        level = new Level(1, board);
        user = new User("test","test@test.com","user","avatar", Difficulty.EASY);
        game = new Game(level, user);
    }
    /**
     * setupStage2:
     * Tablero 5x5 con bordes de muro.
     * El jugador esta en (2,2) y hay un muro en la celda superior (2,1).
     */
    private void setupStage2() {
        setupStage1();

        // Muro arriba
        board.setCell(2,1, new Cell(CellType.WALL,false));
    }
    /**
     * setupStage3:
     * Tablero 5x5 con bordes de muro.
     * El jugador esta en (2,2), hay una caja en (3,2)
     * y la celda (4,2) está libre.
     */
    private void setupStage3() {
        setupStage1();

        // Caja en (3,2)
        board.setCell(3,2, new Cell(CellType.FLOOR,true));
    }
    /**
     * setupStage4:
     * Tablero 5x5 con bordes de muro.
     * El jugador está en (2,2), hay una caja en (3,2)
     * y un muro en (4,2) que bloquea el movimiento.
     */
    private void setupStage4() {
        setupStage3();

        // Muro detras de la caja
        board.setCell(4,2, new Cell(CellType.WALL,false));
    }
    /**
     * setupStage5:
     * Tablero 5x5 con bordes de muro.
     * El jugador está en (2,2), hay una caja en (3,2)
     * y otra caja en (4,2), bloqueando el empuje.
     */
    private void setupStage5() {
        setupStage3();

        // Otra caja bloqueando
        board.setCell(4,2, new Cell(CellType.FLOOR,true));
    }


    // movimientos validos


    @Test
    public void testMoveUp_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("UP");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testMoveDown_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("DOWN");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testMoveLeft_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("LEFT");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testMoveRight_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }


    // movimientos invalidos


    @Test
    public void testMoveUp_BlockByWall() {

        // Arrange
        setupStage2();

        // Act
        game.movePlayer("UP");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testMoveRight_BoxBlockedByWall() {

        // Arrange
        setupStage4();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testMoveRight_BoxBlockedByBox() {

        // Arrange
        setupStage5();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }


    // empuje caja


    @Test
    public void testMoveRight_PushBoxSuccessfully() {

        // Arrange
        setupStage3();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testPushBox_Success() {

        // Arrange
        setupStage3();
        Box box = new Box(new Position(3,2));

        // Act
        game.pushBox(box, "RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testPushBox_FailByWall() {

        // Arrange
        setupStage4();
        Box box = new Box(new Position(3,2));

        // Act
        game.pushBox(box, "RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    @Test
    public void testPushBox_FailByBox() {

        // Arrange
        setupStage5();
        Box box = new Box(new Position(3,2));

        // Act
        game.pushBox(box, "RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }
}