package structures;

/**
 * Implementación de una Pila (Stack) usando nodos.
 * Estructura LIFO (Last In, First Out)
 */
public class Stack<E> {

    private Node<E> top;   // cima de la pila
    private int size;

    // Constructor
    public Stack() {
        top = null;
        size = 0;
    }


}