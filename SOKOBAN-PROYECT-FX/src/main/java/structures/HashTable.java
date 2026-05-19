package structures;

public class HashTable<K, V> {

    private HashNode<K, V>[] buckets;
    private int capacity;
    private int size;

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.size     = 0;
        this.buckets  = new HashNode[capacity];
    }


    /**
     * Calcula el índice de la cubeta para una clave dada.
     * Garantiza un resultado no negativo.
     * @param key clave cuyo índice se calcula
     * @return índice de la cubeta entre 0 y capacity-1
     */
    private int getIndex(K key) {
        int hash = key.hashCode();
        if (hash < 0) hash = -hash;
        return hash % capacity;
    }

    /**
     * Inserta un par clave-valor en la tabla.
     * Si la clave ya existe, actualiza su valor.
     *
     * @param key   clave del par; no debe ser nula
     * @param value valor asociado a la clave
     */
    public void put(K key, V value) {
        int index = getIndex(key);
        HashNode<K, V> head = buckets[index];


        while (head != null) {
            if (head.getKey().equals(key)) {
                head.setValue(value);
                return;
            }
            head = head.getNext();
        }


        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.setNext(buckets[index]);
        buckets[index] = newNode;
        size++;
    }
    /**
     * Busca y retorna el valor asociado a la clave dada.
     * @param key clave a buscar
     * @return el valor asociado, o {@code null} si la clave no existe
     */
    public V get(K key) {
        int index = getIndex(key);
        HashNode<K, V> head = buckets[index];

        while (head != null) {
            if (head.getKey().equals(key)) {
                return head.getValue();
            }
            head = head.getNext();
        }

        return null;
    }
    /**
     * Elimina el par con la clave dada y retorna su valor.
     *
     * @param key clave del par a eliminar
     * @return el valor eliminado, o {@code null} si la clave no existía
     */
    public V remove(K key) {
        int index = getIndex(key);
        HashNode<K, V> head = buckets[index];
        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.getKey().equals(key)) {
                if (prev == null) {
                    buckets[index] = head.getNext();
                } else {
                    prev.setNext(head.getNext());
                }
                size--;
                return head.getValue();
            }
            prev = head;
            head = head.getNext();
        }

        return null;
    }

    /**
     * Indica si la tabla contiene la clave dada.
     * @param key clave a verificar
     * @return {@code true} si la clave existe, {@code false} en caso contrario
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Vacía la tabla eliminando todos los pares clave-valor.
     */
    public void clear() {
        buckets = new HashNode[capacity];
        size    = 0;
    }

    /**
     * Indica si la tabla no contiene ningún par.
     * @return true si la tabla está vacía
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna el número de pares clave-valor almacenados en la tabla.
     * @return cantidad de entradas en la tabla
     */
    public int size() {
        return size;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HashTable {\n");
        for (int i = 0; i < capacity; i++) {
            if (buckets[i] != null) {
                sb.append("  [").append(i).append("]: ");
                HashNode<K, V> current = buckets[i];
                while (current != null) {
                    sb.append(current);
                    if (current.getNext() != null) sb.append(" -> ");
                    current = current.getNext();
                }
                sb.append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }



}