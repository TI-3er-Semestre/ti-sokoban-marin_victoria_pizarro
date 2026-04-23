package structures;

/**
 * Nodo genérico para la lista doblemente enlazada.
 * Contiene un dato de tipo E, y referencias al nodo anterior y siguiente.
 *
 * @param <E> Tipo de dato almacenado en el nodo
 */
public class Node<E> {

    private E data;
    private Node<E> next;
    private Node<E> prev;

    /**
     * Constructor que inicializa el nodo con un dato.
     * Las referencias next y prev quedan en null.
     *
     * @param data Dato a almacenar
     */
    public Node(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────────

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return String.valueOf(data);
    }
}