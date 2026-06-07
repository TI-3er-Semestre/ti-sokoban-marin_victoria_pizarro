import model.*;
import structures.LinkedList;
import structures.CustomIterator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DFSAutoTest {

    private Board     board;
    private Level     level;
    private Game      game;
    private DFSAuto   solver;
    private GameState initialState;

    /**
     * setupStage34:
     * Tablero 3x3 con muros en toda la periferia.
     * La celda (1,1) es GOAL y ya tiene una caja encima.
     * El GameState inicial se construye directamente con la caja sobre el goal.
     */
    private void setupStage34() {
        board = new Board(3, 3);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.GOAL, false));
        board.addBox(new Box(new Position(1, 1)));

        level  = new Level(34, board, new Position(1,1));
        game   = new Game(level, null);
        game.startGame();

        solver = new DFSAuto(board);

        LinkedList<Box> initialBoxes = new LinkedList<>();
        initialBoxes.add(new Box(new Position(1, 1)));
        initialState = new GameState(new Position(0, 0), initialBoxes, null, null);
    }

    /**
     * setupStage35:
     * Tablero 5x3 con muros en toda la periferia.
     * Jugador en (1,1), caja en (2,1), goal en (3,1).
     * Un único empuje a la derecha resuelve el nivel.
     */
    private void setupStage35() {
        board = new Board(5, 3);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.FLOOR, false));
        board.setCell(2, 1, new Cell(CellType.FLOOR, false));
        board.setCell(3, 1, new Cell(CellType.GOAL,  false));

        board.addBox(new Box(new Position(2, 1)));

        level  = new Level(35, board, new Position(1,1));
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(1, 1));

        solver       = new DFSAuto(board);
        initialState = solver.buildInitialState(game);
    }

    /**
     * setupStage36:
     * Tablero 5x3 con muros en toda la periferia.
     * Goal en (1,1), caja en (2,1), jugador en (3,1).
     * Un único empuje a la izquierda resuelve el nivel.
     */
    private void setupStage36() {
        board = new Board(5, 3);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(1, 1, new Cell(CellType.GOAL,  false));
        board.setCell(2, 1, new Cell(CellType.FLOOR, false));
        board.setCell(3, 1, new Cell(CellType.FLOOR, false));

        board.addBox(new Box(new Position(2, 1)));

        level  = new Level(36, board, new Position(1,1));
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(3, 1));

        solver       = new DFSAuto(board);
        initialState = solver.buildInitialState(game);
    }

    /**
     * setupStage37:
     * Tablero 3x3 con muros en toda la periferia.
     * Goal en (0,1), caja en (1,1), muro en (2,1).
     * La caja no tiene solución porque el jugador no puede
     * posicionarse detrás de ella para empujarla hacia el goal.
     */
    private void setupStage37() {
        board = new Board(3, 3);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        board.setCell(0, 1, new Cell(CellType.GOAL,  false));
        board.setCell(1, 1, new Cell(CellType.FLOOR, false));

        board.addBox(new Box(new Position(1, 1)));

        level  = new Level(37, board, new Position(1,1));
        game   = new Game(level, null);
        game.startGame();

        solver = new DFSAuto(board);

        LinkedList<Box> initialBoxes = new LinkedList<>();
        initialBoxes.add(new Box(new Position(1, 1)));
        initialState = new GameState(new Position(0, 0), initialBoxes, null, null);
    }

    /**
     * setupStage38:
     * Tablero 5x5 con muros en la periferia e interior 3x3 caminable.
     * Goal en (2,1), caja en (2,2), jugador en (1,3).
     * DFS debe encontrar una solución incluyendo
     * el reposicionamiento del jugador antes de empujar.
     */
    private void setupStage38() {
        board = new Board(5, 5);

        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                board.setCell(x, y, new Cell(CellType.WALL, false));

        for (int x = 1; x <= 3; x++)
            for (int y = 1; y <= 3; y++)
                board.setCell(x, y, new Cell(CellType.FLOOR, false));

        board.setCell(2, 1, new Cell(CellType.GOAL, false));
        board.addBox(new Box(new Position(2, 2)));

        level  = new Level(38, board, new Position(1,1));
        game   = new Game(level, null);
        game.startGame();
        game.getPlayer().setPosition(new Position(1, 3));

        solver       = new DFSAuto(board);
        initialState = solver.buildInitialState(game);
    }

    /**
     * Verifica que solve() retorna una lista vacía (no null)
     * cuando la caja ya está sobre el goal desde el inicio.
     */
    @Test
    public void test34_boxAlreadyOnGoal_returnsEmptyList() {
        // Arrange
        setupStage34();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertEquals(0, solution.getLength());
    }

    /**
     * Verifica que solve() encuentra una solución de exactamente
     * 1 movimiento cuando la caja está a un empuje del goal.
     */
    @Test
    public void test35_solutionHasOneMove() {
        // Arrange
        setupStage35();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertEquals(1, solution.getLength());
    }

    /**
     * Verifica que el movimiento encontrado por DFS es RIGHT.
     */
    @Test
    public void test35_theMoveIsRight() {
        // Arrange
        setupStage35();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertEquals("RIGHT", solution.getFirst().getData());
    }

    /**
     * Verifica que al aplicar la solución de DFS sobre Game,
     * checkWin() retorna true.
     */
    @Test
    public void test35_applyingSolutionWinsLevel() {
        // Arrange
        setupStage35();
        LinkedList<String> solution = solver.solve(initialState);
        Assertions.assertNotNull(solution);

        // Act
        CustomIterator<String> it = solution.iterator();
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
    public void test36_solutionHasOneMove() {
        // Arrange
        setupStage36();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertEquals(1, solution.getLength());
    }

    /**
     * Verifica que el movimiento encontrado por DFS es LEFT.
     */
    @Test
    public void test36_theMoveIsLeft() {
        // Arrange
        setupStage36();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertEquals("LEFT", solution.getFirst().getData());
    }

    /**
     * Verifica que solve() retorna null cuando la caja
     * no tiene solución posible por estar bloqueada entre muros.
     */
    @Test
    public void test37_noSolution_returnsNull() {
        // Arrange
        setupStage37();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNull(solution);
    }

    /**
     * Verifica que solve() encuentra una solución cuando
     * el jugador necesita reposicionarse antes de empujar.
     */
    @Test
    public void test38_solutionExists() {
        // Arrange
        setupStage38();

        // Act
        LinkedList<String> solution = solver.solve(initialState);

        // Assert
        Assertions.assertNotNull(solution);
        Assertions.assertTrue(solution.getLength() > 0);
    }

    /**
     * Verifica que al aplicar la solución de DFS sobre Game,
     * checkWin() retorna true incluso cuando se requiere reposicionamiento.
     */
    @Test
    public void test38_applyingSolutionWinsLevel() {
        // Arrange
        setupStage38();
        LinkedList<String> solution = solver.solve(initialState);
        Assertions.assertNotNull(solution);

        // Act
        CustomIterator<String> it = solution.iterator();
        while (it.hasNext()) {
            game.movePlayer(it.next().getData());
        }

        // Assert
        Assertions.assertTrue(game.checkWin());
    }

    /**
     * Verifica que la solución de DFS es válida: aplicando un paso menos
     * que la solución encontrada, el nivel no debe estar ganado.
     */
    @Test
    public void test38_solutionIsValid() {
        // Arrange
        setupStage38();
        LinkedList<String> solution = solver.solve(initialState);
        Assertions.assertNotNull(solution);
        int solutionLength = solution.getLength();

        if (solutionLength > 1) {
            game.startGame();
            game.getPlayer().setPosition(new Position(1, 3));

            // Act
            CustomIterator<String> it = solution.iterator();
            int steps = 0;
            while (it.hasNext() && steps < solutionLength - 1) {
                game.movePlayer(it.next().getData());
                steps++;
            }

            // Assert
            Assertions.assertFalse(game.checkWin());
        }
    }
}