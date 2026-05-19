import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.PriorityQueue;

public class PriorityQueueTest {

    private PriorityQueue cola;
    private GameResult resultado;
    private Leaderboard leaderboard;
    private User user;

    // CONFIGURACIÓN DE ESCENARIOS

    /**
     * setupStage15:
     * Cola vacía con capacidad 5.
     */
    private void setupStage15() {
        cola = new PriorityQueue(5);
    }

    /**
     * setupStage16:
     * Cola con tres resultados: 10 mov, 5 mov, 8 mov.
     */
    private void setupStage16() {
        cola = new PriorityQueue(5);

        user = new User("Ana Torres", "ana@mail.com", "ana123", "avatar1.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 10, 2, 60));

        user = new User("Luis Gomez", "luis@mail.com", "luis123", "avatar2.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 5, 1, 30));

        user = new User("Pedro Ruiz", "pedro@mail.com", "pedro123", "avatar3.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 8, 3, 45));
    }

    /**
     * setupStage17:
     * Cola llena (capacidad 3) con resultados de 5, 8 y 12 movimientos.
     */
    private void setupStage17() {
        cola = new PriorityQueue(3);

        user = new User("A", "a@mail.com", "a1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 5, 1, 30));

        user = new User("B", "b@mail.com", "b1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 8, 2, 45));

        user = new User("C", "c@mail.com", "c1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 12, 3, 60));
    }

    /**
     * setupStage18:
     * Cola llena (capacidad 3) con resultados de 5, 7 y 10 movimientos.
     */
    private void setupStage18() {
        cola = new PriorityQueue(3);

        user = new User("A", "a@mail.com", "a1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 5, 1, 30));

        user = new User("B", "b@mail.com", "b1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 7, 2, 40));

        user = new User("C", "c@mail.com", "c1", "avatar.png", Difficulty.EASY);
        cola.insert(new GameResult(user, 1, 10, 3, 55));
    }

    /**
     * setupStage19:
     * GameResult con usuario "juan123", nivel 1, 10 movimientos, 3 empujes, 120 segundos.
     */
    private void setupStage19() {
        user = new User("Juan Perez", "juan@mail.com", "juan123", "avatar.png", Difficulty.EASY);
        resultado = new GameResult(user, 1, 10, 3, 120);
    }

    /**
     * setupStage20:
     * Leaderboard vacío para el nivel 1.
     */
    private void setupStage20() {
        leaderboard = new Leaderboard(1);
    }

    /**
     * setupStage21:
     * Leaderboard con 3 resultados: 10, 15 y 20 movimientos.
     */
    private void setupStage21() {
        leaderboard = new Leaderboard(1);

        user = new User("A", "a@mail.com", "a1", "avatar.png", Difficulty.EASY);
        leaderboard.addResult(new GameResult(user, 1, 10, 2, 60));

        user = new User("B", "b@mail.com", "b1", "avatar.png", Difficulty.EASY);
        leaderboard.addResult(new GameResult(user, 1, 15, 3, 80));

        user = new User("C", "c@mail.com", "c1", "avatar.png", Difficulty.EASY);
        leaderboard.addResult(new GameResult(user, 1, 20, 4, 100));
    }

    /**
     * setupStage22:
     * Leaderboard lleno (top 5) con resultados de 5, 7, 8, 9 y 10 movimientos.
     */
    private void setupStage22() {
        leaderboard = new Leaderboard(1);
        int[] movimientos = {5, 7, 8, 9, 10};
        String[] usernames = {"a1", "b1", "c1", "d1", "e1"};

        for (int i = 0; i < 5; i++) {
            user = new User("User" + i, "u" + i + "@mail.com",
                    usernames[i], "avatar.png", Difficulty.EASY);
            leaderboard.addResult(new GameResult(user, 1,
                    movimientos[i], i + 1, 60 + i * 10));
        }
    }

    // =========================================================================
    // PRUEBAS DE insert()
    // =========================================================================

    /**
     * Caso estándar: insertar en cola vacía.
     * Se espera que cantidadActual sea 1 y peekMin retorne el resultado insertado.
     */
    @Test
    public void insertIntoEmptyQueueTest() {
        // Arrange
        setupStage15();
        user = new User("Ana Torres", "ana@mail.com", "ana123", "avatar.png", Difficulty.EASY);
        GameResult nuevoResultado = new GameResult(user, 1, 10, 2, 60);

        // Act
        cola.insert(nuevoResultado);

        // Assert
        Assertions.assertEquals(1, cola.getSize());
        Assertions.assertEquals("ana123", cola.peekMin().getPlayerName());
    }

    /**
     * Caso interesante: insertar un resultado mejor que la raíz actual.
     * Se espera que el nuevo resultado suba a la raíz del heap.
     */
    @Test
    public void insertBetterResultBecomesRootTest() {
        // Arrange
        setupStage16();
        user = new User("Carlos Diaz", "carlos@mail.com", "carlos123", "avatar.png", Difficulty.EASY);
        GameResult mejorResultado = new GameResult(user, 1, 3, 1, 20);

        // Act
        cola.insert(mejorResultado);

        // Assert
        Assertions.assertEquals(4, cola.getSize());
        Assertions.assertEquals(3, cola.peekMin().getMoves());
    }

    /**
     * Caso límite: insertar en cola llena.
     * Se espera que el tamaño no cambie y el resultado se ignore.
     */
    @Test
    public void insertIntoFullQueueIsIgnoredTest() {
        // Arrange
        setupStage17();
        user = new User("Extra", "extra@mail.com", "extra1", "avatar.png", Difficulty.EASY);
        GameResult resultadoExtra = new GameResult(user, 1, 6, 1, 40);

        // Act
        cola.insert(resultadoExtra);

        // Assert
        Assertions.assertEquals(3, cola.getSize());
    }

    // =========================================================================
    // PRUEBAS DE extractMin()
    // =========================================================================

    /**
     * Caso estándar: extraer el mejor resultado de una cola con tres elementos.
     * Se espera que retorne el de menor movimientos y el tamaño baje a 2.
     */
    @Test
    public void extractMinReturnsLowestMovesTest() {
        // Arrange
        setupStage16();

        // Act
        GameResult extraido = cola.extractMin();

        // Assert
        Assertions.assertEquals(5, extraido.getMoves());
        Assertions.assertEquals(2, cola.getSize());
    }

    /**
     * Caso límite: extraer de cola vacía.
     * Se espera que retorne null.
     */
    @Test
    public void extractMinFromEmptyQueueReturnsNullTest() {
        // Arrange
        setupStage15();

        // Act
        GameResult extraido = cola.extractMin();

        // Assert
        Assertions.assertNull(extraido);
    }

    /**
     * Caso interesante: extraer todos los elementos uno a uno y verificar
     * que salen en orden ascendente de movimientos.
     */
    @Test
    public void extractAllElementsInAscendingOrderTest() {
        // Arrange
        setupStage16();
        int[] ordenEsperado = {5, 8, 10};

        // Act & Assert
        for (int movimientosEsperados : ordenEsperado) {
            Assertions.assertEquals(movimientosEsperados, cola.extractMin().getMoves());
        }
        Assertions.assertTrue(cola.isEmpty());
    }

    // =========================================================================
    // PRUEBAS DE tryAdd()
    // =========================================================================

    /**
     * Caso estándar: tryAdd en cola vacía inserta directamente.
     */
    @Test
    public void tryAddIntoEmptyQueueTest() {
        // Arrange
        setupStage15();
        user = new User("Ana Torres", "ana@mail.com", "ana123", "avatar.png", Difficulty.EASY);
        GameResult nuevoResultado = new GameResult(user, 1, 10, 2, 60);

        // Act
        cola.tryAdd(nuevoResultado);

        // Assert
        Assertions.assertEquals(1, cola.getSize());
    }

    /**
     * Caso interesante: tryAdd con resultado mejor que el peor del top-K lleno.
     * Se espera que el peor sea reemplazado y el tamaño no cambie.
     */
    @Test
    public void tryAddBetterResultReplacesWorstTest() {
        // Arrange
        setupStage18();
        user = new User("Nuevo", "nuevo@mail.com", "nuevo1", "avatar.png", Difficulty.EASY);
        GameResult resultadoMejor = new GameResult(user, 1, 3, 1, 20);

        // Act
        cola.tryAdd(resultadoMejor);

        // Assert
        Assertions.assertEquals(3, cola.getSize());
        Assertions.assertEquals(3, cola.peekMin().getMoves());
    }

    /**
     * Caso límite: tryAdd con resultado peor que todos los del top-K lleno.
     * Se espera que la cola no se modifique.
     */
    @Test
    public void tryAddWorseResultDoesNotModifyQueueTest() {
        // Arrange
        setupStage18();
        user = new User("Malo", "malo@mail.com", "malo1", "avatar.png", Difficulty.EASY);
        GameResult resultadoPeor = new GameResult(user, 1, 50, 10, 300);
        int tamanoAntes = cola.getSize();

        // Act
        cola.tryAdd(resultadoPeor);

        // Assert
        Assertions.assertEquals(tamanoAntes, cola.getSize());
        Assertions.assertEquals(5, cola.peekMin().getMoves());
    }

    // =========================================================================
    // PRUEBAS DE GameResult
    // =========================================================================

    /**
     * Caso estándar: getPlayerName() retorna el username del User correctamente.
     */
    @Test
    public void gameResultGetPlayerNameTest() {
        // Arrange
        setupStage19();

        // Act
        String nombre = resultado.getPlayerName();

        // Assert
        Assertions.assertEquals("juan123", nombre);
    }

    /**
     * Caso estándar: getMoves() retorna los movimientos correctos.
     */
    @Test
    public void gameResultGetMovesTest() {
        // Arrange
        setupStage19();

        // Act
        int movimientos = resultado.getMoves();

        // Assert
        Assertions.assertEquals(10, movimientos);
    }

    /**
     * Caso interesante: compareTo() con resultado de menos movimientos
     * debe retornar positivo porque el actual tiene más movimientos.
     */
    @Test
    public void gameResultCompareToWithBetterResultTest() {
        // Arrange
        setupStage19();
        user = new User("Otro", "otro@mail.com", "otro1", "avatar.png", Difficulty.EASY);
        GameResult resultadoMejor = new GameResult(user, 1, 5, 1, 30);

        // Act
        int comparacion = resultado.compareTo(resultadoMejor);

        // Assert
        Assertions.assertTrue(comparacion > 0);
    }

    // =========================================================================
    // PRUEBAS DE Leaderboard
    // =========================================================================

    /**
     * Caso estándar: addResult en leaderboard vacío inserta directamente.
     */
    @Test
    public void leaderboardAddResultToEmptyTest() {
        // Arrange
        setupStage20();
        user = new User("Ana Torres", "ana@mail.com", "ana123", "avatar.png", Difficulty.EASY);
        GameResult nuevoResultado = new GameResult(user, 1, 10, 2, 60);

        // Act
        leaderboard.addResult(nuevoResultado);

        // Assert
        Assertions.assertEquals(1, leaderboard.getBestResults().getSize());
    }

    /**
     * Caso interesante: addResult con resultado mejor que el peor del top-K lleno.
     * Se espera que el peor sea reemplazado y el tamaño siga en 5.
     */
    @Test
    public void leaderboardAddBetterResultReplacesWorstTest() {
        // Arrange
        setupStage22();
        user = new User("Nuevo", "nuevo@mail.com", "nuevo1", "avatar.png", Difficulty.EASY);
        GameResult resultadoMejor = new GameResult(user, 1, 3, 1, 20);

        // Act
        leaderboard.addResult(resultadoMejor);

        // Assert
        Assertions.assertEquals(5, leaderboard.getBestResults().getSize());
        Assertions.assertEquals(3, leaderboard.getBestResults().peekMin().getMoves());
    }

    /**
     * Caso límite: addResult con resultado peor que todos los del top-K lleno.
     * Se espera que la cola no se modifique.
     */
    @Test
    public void leaderboardAddWorseResultDoesNotModifyTest() {
        // Arrange
        setupStage22();
        user = new User("Malo", "malo@mail.com", "malo1", "avatar.png", Difficulty.EASY);
        GameResult resultadoPeor = new GameResult(user, 1, 50, 10, 300);
        int tamanoAntes = leaderboard.getBestResults().getSize();

        // Act
        leaderboard.addResult(resultadoPeor);

        // Assert
        Assertions.assertEquals(tamanoAntes, leaderboard.getBestResults().getSize());
    }
}