package structures;

/**
 * Implementación concreta de Iterator para la lista doblemente enlazada.
 * Recorre los nodos de principio a fin usando el puntero next.
 *
 * @param <E> Tipo de dato almacenado en los nodos
 */
public class CustomIterator<E> implements Iterator<E> {

    // Nodo actual (cursor interno del iterador)
    private Node<E> current;

    /**
     * Constructor: el cursor se posiciona en el primer nodo de la lista.
     *
     * @param first Nodo cabeza de la lista
     */
    public CustomIterator(Node<E> first) {
        this.current = first;
    }

    /**
     * Indica si hay un nodo más por visitar.
     */
    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * Retorna el dato del nodo actual y avanza el cursor al siguiente nodo.
     */
    @Override
    public Node<E> next() {
        Node<E> node = current;
        current = current.getNext();
        return node;
    }

    // ── Getter ────────────────────────────────────────────────────────────────

    public Node<E> getCurrent() {
        return current;
    }
}