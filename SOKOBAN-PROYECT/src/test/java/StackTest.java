import structures.Stack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackTest {
    private Stack<Integer> stack;
    /**
     * setupStage27:
     * Pila vacía
     */
    private void setupStage27() {
        stack = new Stack<>();
    }

    /**
     * setupStage28:
     * Pila con [10, 20] (20 en la cima)
     */
    private void setupStage28() {
        stack = new Stack<>();
        stack.push(10);
        stack.push(20);
    }




    @Test
    // verifica que al insertar elementos con push(), el tamano de la pila
    // se incremente correctamente
    void testPushAndSize() {
        // Arrange
        setupStage27();

        // Act
        stack.push(10);
        stack.push(20);
        // Assert

        assertEquals(2, stack.size());//Después de hacer push(10) y push(20), el tamaño debe ser 2.
        // la pila noo pude estar vacia despues de estas operaciones
    }

    /**TestPop
     * comprueba que pop() elimina y devuelve un elemento que esta en la cima de la
     * pila, respetando el orden lifo
     * -
     */

    @Test
    void testPop(){
        //Arrange
        setupStage28();



        //Act
        int result = stack.pop();

        // Assert
        assertEquals(20,result);
        assertEquals(1, stack.size());
    }

    /** TestPeek
     * Asegura que peek() devuelve el elemento de la cima sin eliminarlo
     * -.
     */
    @Test
    void testPeek() {
        // arrange
        setupStage28();

        // Act
        int result = stack.peek();
        // Assert

        assertEquals(20, result);
        assertEquals(2, stack.size()); // no elimina
    }

    /** TestIsEmpty
     *  verifica que el metodo isEmpty() refleje correctamente si la pila tiene elementos o no
     *
     */
    @Test
    void testIsEmpty() {
        // Arrange
        setupStage27();
        // Assert
        assertTrue(stack.isEmpty());
        // act

        stack.push(1);
        // Assert

        assertFalse(stack.isEmpty());
    }

    /** TestPopEmpty
     * comprubea el comportamiento de pop() cuando se invoca sobre una pila sin elementos
     * - Si la pila está vacía, pop() debe retornar null (en lugar de lanzar una excepción).
     *
     *
     */

    @Test
    void testPopEmpty() {
        // Arrange
        setupStage27();

        // ACT y Asser

        assertNull(stack.pop());
    }

    /** testEmpty
     *  asegura el comportamiento de peek() cuando la pila no contiene elementos
     *
     */

    @Test
    void testPeekEmpty() {
        // Arrange
        setupStage27();
        // Act y assert

        assertNull(stack.peek());
    }

    /** testDisplay
     * verifica que el metoo display() genera una representacion textual del contenido
     * de la pila que inluy todos los metodos almacenados
     *
     */
    @Test
    void testDisplay() {
        // Arrange
       setupStage28();
       //Act


        String result = stack.display();
        // Assert

        assertTrue(result.contains("2"));
        assertTrue(result.contains("1"));
    }

    /** testGeneric
     * Comprueba que la clase Stack es generica, es decir que pueda manejar cualquier tipo de dato
     * integer,string,object
     *
     */

    @Test
    void testGeneric() {
        // rrange
        Stack<String> stack = new Stack<>();
        // Act
        stack.push("A");
        stack.push("B");
        // Asserr
        assertEquals("B", stack.pop());
    }



}