package structures;

/**
 * Interfaz genérica Iterable.
 * Cualquier colección que implemente esta interfaz puede entregar
 * un iterador para recorrer sus elementos.
 *
 * @param <E> Tipo de elemento de la colección
 */
public interface Iterable<E> {

    /**
     * Retorna un iterador posicionado al inicio de la colección.
     *
     * @return Iterador sobre los elementos de tipo E
     */
    CustomIterator<E> iterator();
}