import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structures.Queue;

public class QueueTest {

    private Queue<Integer> queue;

    /**
     * setupStage1:
     * Cola con tres elementos encolados en orden: 10, 20, 30.
     * El frente de la cola es 10.
     */
    private void setupStage1() {
        queue = new Queue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
    }

    /**
     * setupStage2:
     * Cola vacía, sin ningún elemento.
     */
    private void setupStage2() {
        queue = new Queue<>();
    }

    /**
     * Verifica que dequeue retorna los elementos
     * en orden FIFO (el primero en entrar es el primero en salir).
     */
    @Test
    public void testDequeue_OrdenFIFO() {

        // Arrange
        setupStage1();

        // Act
        int primero = queue.dequeue();
        int segundo = queue.dequeue();
        int tercero = queue.dequeue();

        // Assert
        Assertions.assertEquals(10, primero);
        Assertions.assertEquals(20, segundo);
        Assertions.assertEquals(30, tercero);
    }

    /**
     * Verifica que peek retorna el elemento al frente
     * sin eliminarlo de la cola.
     */
    @Test
    public void testPeek_NoEliminaElemento() {

        // Arrange
        setupStage1();

        // Act
        int frente = queue.peek();

        // Assert
        Assertions.assertEquals(10, frente);
        Assertions.assertEquals(3, queue.size());
    }

    /**
     * Verifica que dequeue y peek retornan null
     * cuando la cola está vacía, e isEmpty retorna true.
     */
    @Test
    public void testColaVacia_RetornaNull() {

        // Arrange
        setupStage2();

        // Act
        Integer resultDequeue = queue.dequeue();
        Integer resultPeek    = queue.peek();

        // Assert
        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertNull(resultDequeue);
        Assertions.assertNull(resultPeek);
    }
}