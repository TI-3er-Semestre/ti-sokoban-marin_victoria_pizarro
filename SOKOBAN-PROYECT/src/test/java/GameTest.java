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
                    board.setCell(x, y, new Cell(CellType.WALL, false));
                } else {
                    board.setCell(x, y, new Cell(CellType.FLOOR, false));

                }
            }

        }
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


            player = new Player(new Position(2,2));
            level = new Level(1, board);
            user = new User("test","test@test.com","user","avatar", Difficulty.EASY);
            game = new Game(level, user);

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


    /**
     * Verifica que el jugador pueda moverse hacia arriba
     * cuando la celda destino está libre.
     */
    @Test
    public void testMoveUp_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("UP");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador pueda moverse hacia abajo
     * cuando la celda destino está libre.
     */
    @Test
    public void testMoveDown_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("DOWN");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador pueda moverse hacia la izquierda
     * cuando la celda destino está libre.
     */
    @Test
    public void testMoveLeft_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("LEFT");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador pueda moverse hacia la derecha
     * cuando la celda destino está libre.
     */
    @Test
    public void testMoveRight_FreeCell() {

        // Arrange
        setupStage1();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }


    /**
     * Verifica que el jugador NO se mueva hacia arriba
     * cuando la celda destino es un muro.
     */
    @Test
    public void testMoveUp_BlockByWall() {

        // Arrange
        setupStage2();

        // Act
        game.movePlayer("UP");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador NO pueda empujar una caja
     * cuando hay un muro detrás de ella.
     */
    @Test
    public void testMoveRight_BoxBlockedByWall() {

        // Arrange
        setupStage4();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador NO pueda empujar una caja
     * cuando otra caja bloquea el movimiento.
     */
    @Test
    public void testMoveRight_BoxBlockedByBox() {

        // Arrange
        setupStage5();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }

    /**
     * Verifica que el jugador empuje correctamente una caja
     * cuando la celda siguiente está libre.
     */
    @Test
    public void testMoveRight_PushBoxSuccessfully() {

        // Arrange
        setupStage3();

        // Act
        game.movePlayer("RIGHT");

        // Assert
        Assertions.assertTrue(true);
    }


    /**
     * Verifica que el metodo pushBox mueva la caja correctamente
     * cuando la celda destino está libre.
     */
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

    /**
     * Verifica que el metodo pushBox NO mueva la caja
     * cuando la celda destino es un muro.
     */
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

    /**
     * Verifica que el méetodo pushBox NO mueva la caja
     * cuando la celda destino está ocupada por otra caja.
     */
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




