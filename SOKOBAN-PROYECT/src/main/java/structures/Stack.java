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
    public boolean isEmpty() {
        return top == null;
    }
    // insewerta un Elemento en la cima, complejidad O(1)


    public void push(E data){
        Node<E> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;

        size ++;
    }
    // elimina y retorna el elemento en la cima, complejidad O(1)

    public E pop() {
        if (isEmpty()) {
            return null;
        }

        E data = top.getData();
        top = top.getNext();

        size--;
        return data;
    }



}