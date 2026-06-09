package structures;


public class Queue<E> {

    private Node<E> front;
    private Node<E> rear;
    private int size;

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param data elemento a insertar
     */
    public void enqueue(E data) {
        Node<E> newNode = new Node<>(data);

        if (isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }

        rear = newNode;
        size++;
    }

    /**
     * Elimina el primer elemento.
     * @return elemento eliminado o null
     */
    public E dequeue() {
        if (isEmpty()) return null;

        E data = front.getData();
        front = front.getNext();

        if (front == null) {
            rear = null;
        }

        size--;
        return data;
    }

    /**
     * Consulta el primero.
     * @return el primer elemento o null
     */
    public E peek() {
        if (isEmpty()) {
            return null;
        } else {
            return front.getData();
        }
    }

    /**
     * Vacía completamente la cola eliminando todos sus elementos.
     */
    public void clear() {
        front = null;
        rear  = null;
        size  = 0;
    }
    /**
     * Indica si la cola no contiene elementos.
     * @return si la cola está vacía,en caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Retorna la cantidad de elementos actualmente en la cola.
     * @return número de elementos en la cola
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Cola vacia";

        StringBuilder sb = new StringBuilder("FRONT -> ");
        Node<E> current = front;
        while (current != null) {
            sb.append("[").append(current.getData()).append("]");
            if (current.getNext() != null) sb.append(" -> ");
            current = current.getNext();
        }
        sb.append(" -> null");
        return sb.toString();
    }
}