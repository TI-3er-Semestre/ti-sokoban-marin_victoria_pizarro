import structures.Stack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    @Test
    // verifica que al insertar elementos con push(), el tamano de la pila
    // se incremente correctamente
    void testPushAndSize() {
        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(2, stack.size());//Después de hacer push(10) y push(20), el tamaño debe ser 2.
        // la pila noo pude estar vacia despues de estas operaciones
    }

    /**TestPop
     * comprueba que pop() elimina y devuelve un elemento que esta en la cima de la
     * pila, respetando el orden lifo
     * - si la pila contiene[1,2](con 2 en la cima), pop() debe retornar 2
     * - Despues de pop(), el tamano debe disminuir en 1( quedar un elemento)
     */

    @Test
    void testPop(){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        int result = stack.pop();
        assertEquals(2,result);
        assertEquals(1, stack.size());
    }

    /** TestPeek
     * Asegura que peek() devuelve el elemento de la cima sin eliminarlo
     * - Con [5, 10] (cima = 10), peek() retorna 10.
     * - El tamaño de la pila no cambia después de invocar peek() (sigue siendo 2).
     */
    @Test
    void testPeek() {
        Stack<Integer> stack = new Stack<>();

        stack.push(5);
        stack.push(10);

        int result = stack.peek();

        assertEquals(10, result);
        assertEquals(2, stack.size()); // no elimina
    }

    /** TestIsEmpty
     *  verifica que el metodo isEmpty() refleje correctamente si la pila tiene elementos o no
     *  - una pila recien creada debe estar vacia  (isEmpty() => true).
     * - Después de insertar al menos un elemento con push(), la pila no debe estar vacía (isEmpty() => false).
     * Este metodo no modifica pila
     */
    @Test
    void testIsEmpty() {
        Stack<Integer> stack = new Stack<>();

        assertTrue(stack.isEmpty());

        stack.push(1);

        assertFalse(stack.isEmpty());
    }

    /** TestPopEmpty
     * comprubea el comportamiento de pop() cuando se invoca sobre una pila sin elementos
     * - Si la pila está vacía, pop() debe retornar null (en lugar de lanzar una excepción).
     * - El tamano permanece en 0 y la pila sigue vacía después de la operación.
     *
     */

    @Test
    void testPopEmpty() {
        Stack<Integer> stack = new Stack<>();

        assertNull(stack.pop());
    }

    /** testEmpty
     *  asegura el comportamiento de peek() cuando la pila no contiene elementos
     *  - Sobre una pila vacía, peek() debe retornar null.
     *
     */

    @Test
    void testPeekEmpty() {
        Stack<Integer> stack = new Stack<>();

        assertNull(stack.peek());
    }



}