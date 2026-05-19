package structures;

/**
 * Interfaz genérica Iterator.
 * Define el contrato para recorrer una colección elemento a elemento.
 *
 * @param <E> Tipo de elemento que se itera
 */
public interface Iterator<E> {

    /**
     * Indica si quedan elementos por recorrer.
     *
     * @return true si hay un elemento siguiente, false en caso contrario
     */
    boolean hasNext();

    /**
     * Retorna el siguiente elemento de la colección y avanza el cursor.
     *
     * @return Elemento siguiente
     */
    Node<E> next();
}