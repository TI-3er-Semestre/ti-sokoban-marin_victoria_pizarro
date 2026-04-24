package structures;

/**
 * Lista doblemente enlazada genérica.
 *
 * Implementa {@link Iterable} para poder recorrerse con un {@link CustomIterator}.
 * Los elementos deben implementar {@link Comparable} para poder insertarse
 * en orden ascendente.
 *
 * Operaciones disponibles:
 *   add()     – inserción ordenada
 *   delete()  – eliminación por referencia de dato
 *   search()  – búsqueda por referencia de dato
 *   display() – representación en cadena de toda la lista
 *
 * @param <T> Tipo de dato almacenado; debe ser Comparable consigo mismo
 */
public class LinkedList<T extends Comparable<T>> implements Iterable<T> {

    private Node<T> first;   // Primer nodo (cabeza)
    private Node<T> last;    // Último nodo (cola)
    private int length;      // Cantidad de nodos

    // ── Constructor ───────────────────────────────────────────────────────────

    public LinkedList() {
        this.first  = null;
        this.last   = null;
        this.length = 0;
    }

    // ── add ───────────────────────────────────────────────────────────────────

    /**
     * Inserta un nuevo nodo de forma <b>ordenada ascendente</b>.
     *
     * Casos contemplados:
     *   1. Lista vacía                  → el nuevo nodo es primero y último.
     *   2. El dato es menor que first   → inserción al inicio.
     *   3. El dato es mayor que last    → inserción al final.
     *   4. Inserción en el medio        → se busca la posición correcta.
     *
     * @param data Dato a insertar (no nulo)
     * @return El nodo recién creado
     */
    public Node<T> add(T data) {
        Node<T> newNode = new Node<>(data);

        // Caso 1: lista vacía
        if (first == null) {
            first = newNode;
            last  = newNode;

            // Caso 2: insertar antes del primero
        } else if (data.compareTo(first.getData()) <= 0) {
            newNode.setNext(first);
            first.setPrev(newNode);
            first = newNode;

            // Caso 3: insertar después del último
        } else if (data.compareTo(last.getData()) >= 0) {
            newNode.setPrev(last);
            last.setNext(newNode);
            last = newNode;

            // Caso 4: buscar posición intermedia
        } else {
            //Iteración
            Node<T> current = first.getNext();
            while (current != null && data.compareTo(current.getData()) > 0) {
                current = current.getNext();
            }
            // current apunta al nodo ANTE el cual insertar
            Node<T> previous = current.getPrev();

            newNode.setNext(current);
            newNode.setPrev(previous);
            previous.setNext(newNode);
            current.setPrev(newNode);
        }
        length++;
        return newNode;
    }

    // ── delete ────────────────────────────────────────────────────────────────

    /**
     * Elimina el nodo cuyo dato sea <em>igual</em> al parámetro recibido
     * (comparado con {@code compareTo} == 0).
     *
     * Casos contemplados:
     *   1. Lista vacía / dato no encontrado → retorna null.
     *   2. El nodo a eliminar es el único   → lista queda vacía.
     *   3. El nodo a eliminar es el primero → first avanza.
     *   4. El nodo a eliminar es el último  → last retrocede.
     *   5. El nodo está en el medio         → se reconectan vecinos.
     *
     * @param data Dato del nodo a eliminar
     * @return El nodo eliminado, o null si no se encontró
     */
    public Node<T> delete(T data) {
        Node<T> target = findNode(data);

        if (target == null) {
            return null; // no existe
        }

        // Caso 2: único nodo
        if (target == first && target == last) {
            first = null;
            last  = null;

            // Caso 3: es el primero
        } else if (target == first) {
            first = first.getNext();
            first.setPrev(null);

            // Caso 4: es el último
        } else if (target == last) {
            last = last.getPrev();
            last.setNext(null);

            // Caso 5: nodo intermedio
        } else {
            Node<T> prev = target.getPrev();
            Node<T> next = target.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }

        // Limpiar referencias del nodo eliminado
        target.setNext(null);
        target.setPrev(null);
        length--;
        return target;
    }

    // ── search ────────────────────────────────────────────────────────────────

    /**
     * Busca un nodo cuyo dato sea igual al parámetro.
     *
     * @param data Dato a buscar
     * @return El nodo encontrado, o null si no existe
     */
    public Node<T> search(T data) {
        return findNode(data);
    }

    // ── display ───────────────────────────────────────────────────────────────

    /**
     * Genera una representación legible de toda la lista, recorriéndola
     * con el {@link CustomIterator}.
     *
     * Formato de salida:
     *   null <-> [dato1] <-> [dato2] <-> ... <-> null
     *
     * @return Cadena que representa la lista
     */
    public String display() {
        if (first == null) {
            return "La lista está vacía.";
        }

        StringBuilder sb = new StringBuilder("null <-> ");
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            sb.append("[").append(it.next()).append("]");
            if (it.hasNext()) {
                sb.append(" <-> ");
            }
        }
        sb.append(" <-> null");
        return sb.toString();
    }

    // ── iterator (Iterable) ───────────────────────────────────────────────────

    /**
     * Retorna un iterador posicionado en el primer nodo.
     */
    @Override
    public CustomIterator<T> iterator() {
        return new CustomIterator<>(first);
    }

    // ── helpers privados ──────────────────────────────────────────────────────

    /**
     * Recorre la lista desde first hasta encontrar un nodo cuyo dato
     * sea igual a {@code data} (compareTo == 0).
     *
     * @param data Dato a buscar
     * @return Nodo encontrado o null
     */
    private Node<T> findNode(T data) {
        Node<T> current = first;
        while (current != null) {
            if (current.getData().compareTo(data) == 0) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    // ── getters ───────────────────────────────────────────────────────────────

    public Node<T> getFirst()  { return first;  }
    public Node<T> getLast()   { return last;   }
    public int     getLength() { return length; }

    public Node<T> addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setPrev(last);
            last.setNext(newNode);
            last = newNode;
        }
        length++;
        return newNode;
    }
}