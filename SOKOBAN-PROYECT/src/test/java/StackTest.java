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


}