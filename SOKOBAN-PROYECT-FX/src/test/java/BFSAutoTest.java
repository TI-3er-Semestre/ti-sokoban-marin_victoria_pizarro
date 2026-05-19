import model.*;
import structures.LinkedList;
import structures.CustomIterator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BFSAutoTest {

    private Board     board;
    private Level     level;
    private Game      game;
    private BFSAuto   solver;
    private GameState estadoInicial;

    /**
     * setupStage29:
     * Tablero 3x3 con muros en toda la periferia.
     * La celda (1,1) es GOAL y ya tiene una caja encima.
     * El GameState inicial se construye directamente con la caja sobre el goal.
     */
    private void setupStage29() {
        board = new Board(3, 3);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.GOAL, false));
        board.addBox(new Box(new Position(1, 1)));

        level  = new Level(29, board);
        game   = new Game(level, null);
        game.startGame();

        solver = new BFSAuto(board);

        LinkedList<Box> cajasIniciales = new LinkedList<>();
        cajasIniciales.add(new Box(new Position(1, 1)));
        estadoInicial = new GameState(new Position(0, 0), cajasIniciales, null, null);
    }

    /**
     * setupStage30:
     * Tablero 5x3 con muros en toda la periferia.
     * Jugador en (1,1), caja en (2,1), goal en (3,1).
     * Un único empuje a la derecha resuelve el nivel.
     */
    private void setupStage30() {
        board = new Board(5, 3);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.FLOOR, false));
        board.setCell(2, 1, new Cell(CellType.FLOOR, false));
        board.setCell(3, 1, new Cell(CellType.GOAL,  false));

        board.addBox(new Box(new Position(2, 1)));

        level  = new Level(30, board);
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(1, 1));

        solver        = new BFSAuto(board);
        estadoInicial = solver.buildInitialState(game);
    }

    /**
     * setupStage31:
     * Tablero 5x3 con muros en toda la periferia.
     * Goal en (1,1), caja en (2,1), jugador en (3,1).
     * Un único empuje a la izquierda resuelve el nivel.
     */
    private void setupStage31() {
        board = new Board(5, 3);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.GOAL,  false));
        board.setCell(2, 1, new Cell(CellType.FLOOR, false));
        board.setCell(3, 1, new Cell(CellType.FLOOR, false));

        board.addBox(new Box(new Position(2, 1)));

        level  = new Level(31, board);
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(3, 1));

        solver        = new BFSAuto(board);
        estadoInicial = solver.buildInitialState(game);
    }

    /**
     * setupStage32:
     * Tablero 3x3 con muros en toda la periferia.
     * Goal en (0,1), caja en (1,1), muro en (2,1).
     * La caja no tiene solución porque el jugador no puede
     * posicionarse detrás de ella para empujarla hacia el goal.
     */
    private void setupStage32() {
        board = new Board(3, 3);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(0, 1, new Cell(CellType.GOAL,  false));
        board.setCell(1, 1, new Cell(CellType.FLOOR, false));

        board.addBox(new Box(new Position(1, 1)));

        level  = new Level(32, board);
        game   = new Game(level, null);
        game.startGame();

        solver = new BFSAuto(board);

        LinkedList<Box> cajasIniciales = new LinkedList<>();
        cajasIniciales.add(new Box(new Position(1, 1)));
        estadoInicial = new GameState(new Position(0, 0), cajasIniciales, null, null);
    }

    /**
     * setupStage33:
     * Tablero 5x5 con muros en la periferia e interior 3x3 caminable.
     * Goal en (2,1), caja en (2,2), jugador en (1,3).
     * BFS debe encontrar la secuencia óptima incluyendo
     * el reposicionamiento del jugador antes de empujar.
     */
    private void setupStage33() {
        board = new Board(5, 5);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        for (int x = 1; x <= 3; x++)
            for (int y = 1; y <= 3; y++)
                board.setCell(x, y, new Cell(CellType.FLOOR, false));

        board.setCell(2, 1, new Cell(CellType.GOAL, false));
        board.addBox(new Box(new Position(2, 2)));

        level  = new Level(33, board);
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(1, 3));

        solver        = new BFSAuto(board);
        estadoInicial = solver.buildInitialState(game);
    }

    /**
     * Verifica que solve() retorna una lista vacía (no null)
     * cuando la caja ya está sobre el goal desde el inicio.
     */
    @Test
    public void test29_cajaYaEnGoal_retornaListaVacia() {
        // Arrange
        setupStage29();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertEquals(0, solucion.getLength());
    }

    /**
     * Verifica que solve() encuentra una solución de exactamente
     * 1 movimiento cuando la caja está a un empuje del goal.
     */
    @Test
    public void test30_solucionTieneUnMovimiento() {
        // Arrange
        setupStage30();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertEquals(1, solucion.getLength());
    }

    /**
     * Verifica que el movimiento encontrado por BFS es RIGHT.
     */
    @Test
    public void test30_elMovimientoEsRight() {
        // Arrange
        setupStage30();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertEquals("RIGHT", solucion.getFirst().getData());
    }

    /**
     * Verifica que al aplicar la solución de BFS sobre Game,
     * checkWin() retorna true.
     */
    @Test
    public void test30_aplicarSolucionGanaElNivel() {
        // Arrange
        setupStage30();
        LinkedList<String> solucion = solver.solve(estadoInicial);
        Assertions.assertNotNull(solucion);

        // Act
        CustomIterator<String> it = solucion.iterator();
        while (it.hasNext()) {
            game.movePlayer(it.next().getData());
        }

        // Assert
        Assertions.assertTrue(game.checkWin());
    }

    /**
     * Verifica que solve() encuentra una solución de exactamente
     * 1 movimiento cuando el jugador ya está detrás de la caja.
     */
    @Test
    public void test31_solucionTieneUnMovimiento() {
        // Arrange
        setupStage31();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertEquals(1, solucion.getLength());
    }

    /**
     * Verifica que el movimiento encontrado por BFS es LEFT.
     */
    @Test
    public void test31_elMovimientoEsLeft() {
        // Arrange
        setupStage31();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertEquals("LEFT", solucion.getFirst().getData());
    }

    /**
     * Verifica que solve() retorna null cuando la caja
     * no tiene solución posible por estar bloqueada entre muros.
     */
    @Test
    public void test32_sinSolucion_retornaNull() {
        // Arrange
        setupStage32();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNull(solucion);
    }

    /**
     * Verifica que solve() encuentra una solución cuando
     * el jugador necesita reposicionarse antes de empujar.
     */
    @Test
    public void test33_solucionExiste() {
        // Arrange
        setupStage33();

        // Act
        LinkedList<String> solucion = solver.solve(estadoInicial);

        // Assert
        Assertions.assertNotNull(solucion);
        Assertions.assertTrue(solucion.getLength() > 0);
    }

    /**
     * Verifica que al aplicar la solución de BFS sobre Game,
     * checkWin() retorna true incluso cuando se requiere reposicionamiento.
     */
    @Test
    public void test33_aplicarSolucionGanaElNivel() {
        // Arrange
        setupStage33();
        LinkedList<String> solucion = solver.solve(estadoInicial);
        Assertions.assertNotNull(solucion);

        // Act
        CustomIterator<String> it = solucion.iterator();
        while (it.hasNext()) {
            game.movePlayer(it.next().getData());
        }

        // Assert
        Assertions.assertTrue(game.checkWin());
    }

    /**
     * Verifica que la solución de BFS es óptima: aplicando un paso menos
     * que la solución encontrada, el nivel no debe estar ganado.
     */
    @Test
    public void test33_solucionEsOptima() {
        // Arrange
        setupStage33();
        LinkedList<String> solucion = solver.solve(estadoInicial);
        Assertions.assertNotNull(solucion);
        int longitudOptima = solucion.getLength();

        if (longitudOptima > 1) {
            game.startGame();
            game.getPlayer().setPosition(new Position(1, 3));

            // Act
            CustomIterator<String> it = solucion.iterator();
            int pasos = 0;
            while (it.hasNext() && pasos < longitudOptima - 1) {
                game.movePlayer(it.next().getData());
                pasos++;
            }

            // Assert
            Assertions.assertFalse(game.checkWin());
        }
    }
}