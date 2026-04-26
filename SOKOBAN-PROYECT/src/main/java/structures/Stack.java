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
    // pus (E data) => O(1)
    // se crea un nodo y se ajustan 2 referencias, to y next
    // no depende del tamano de la pila


    public void push(E data){
        Node<E> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;

        size ++;
    }
    // elimina y retorna el elemento en la cima, complejidad O(1)
    // no hay recorrido

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
    // solo retorna el dato del nodo en la cima
    public E peek(){
        if (isEmpty()){
            return null;
        }
        return top.getData();
    }
    // verifica si la pila esta vacia
    // solo compara si el top es null, O(1)
    public boolean isEmpty() {
        return top == null;
    }
    // size() => O(1)
    // retorna una variable almacenada


    public int size(){
        return size;
    }
    // muestra la pila de arriba hacia abajo
    // display() => O(n)
    //ya que se almacenan en n nodos de memoria
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