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
    // retorna el elemento en  la cima sin eliminarlo, complejidad O(1)
    public E peek(){
        if (isEmpty()){
            return null;
        }
        return top.getData();
    }
    // verifica si la pila esta vacia
    public boolean isEmpty() {
        return top == null;
    }

    public int size(){
        return size;
    }
    // muestra la pila de arriba hacia abajo
    public String display(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = top;

        while (current != null ){
            sb.append(current.getData()).append("\n");
            current = current.getNext();
        }
        return  sb.toString();
    }



}