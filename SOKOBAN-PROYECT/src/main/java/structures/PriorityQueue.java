package structures;

import model.GameResult;

/**
 * Cola de prioridad implementada como Min-Heap en array.
 * Mantiene el top-K de mejores resultados por nivel (menos movimientos).
 *
 * Un Min-Heap garantiza que el elemento con MENOR valor de movimientos
 * siempre esté en la raíz (índice 0), lo que permite acceder al mejor
 * resultado y mantener el orden con cada inserción/extracción.
 *
 * Representación del árbol en array:
 *   - Padre de i          -> (i - 1) / 2
 *   - Hijo izquierdo de i -> 2 * i + 1
 *   - Hijo derecho de i   -> 2 * i + 2
 *
 *
 */
public class PriorityQueue {

    private GameResult[] almacenResultados;  // array que representa el heap internamente
    private int cantidadActual;              // cuántos elementos hay en el heap ahora mismo
    private int capacidadMaxima;             // tope máximo de elementos (el K del top-K)

    /**
     * Constructor: inicializa la cola vacía con una capacidad máxima definida.
     * La capacidad máxima define el "K" del top-K (cuántos mejores resultados guardar).
     *
     * @param capacidadMaxima cantidad máxima de resultados que puede guardar la cola
     */
    public PriorityQueue(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.almacenResultados = new GameResult[capacidadMaxima];
        this.cantidadActual = 0;
    }

    // MÉTODOS PRIVADOS HEAP

    /**
     * Retorna el índice del padre del nodo en la posición dada.
     * En un heap representado como array, el padre de cualquier nodo i
     * siempre está en la posición (i - 1) / 2.
     *
     * @param indiceHijo índice del nodo hijo
     * @return índice del nodo padre
     */
    private int indicePadre(int indiceHijo) {
        return (indiceHijo - 1) / 2;
    }

    /**
     * Retorna el índice del hijo izquierdo del nodo en la posición dada.
     * En un heap representado como array, el hijo izquierdo de i
     * siempre está en la posición 2 * i + 1.
     *
     * @param indicePadre índice del nodo padre
     * @return índice del hijo izquierdo
     */
    private int indiceHijoIzquierdo(int indicePadre) {
        return 2 * indicePadre + 1;
    }

    /**
     * Retorna el índice del hijo derecho del nodo en la posición dada.
     * En un heap representado como array, el hijo derecho de i
     * siempre está en la posición 2 * i + 2.
     *
     * @param indicePadre índice del nodo padre
     * @return índice del hijo derecho
     */
    private int indiceHijoDerecho(int indicePadre) {
        return 2 * indicePadre + 2;
    }


    // MÉTODOS PRIVADOS HEAP

    /**
     * Compara dos nodos del heap y determina si el primero tiene mayor prioridad.
     * En este Min-Heap, mayor prioridad significa MENOS movimientos.
     * Es decir, el jugador que completó el nivel con menos movimientos
     * tiene prioridad y debe estar más cerca de la raíz.
     *
     * @param indicePrimero  índice del primer nodo a comparar
     * @param indiceSegundo  índice del segundo nodo a comparar
     * @return true si el primero tiene más prioridad (menos movimientos) que el segundo
     */
    private boolean tieneMayorPrioridad(int indicePrimero, int indiceSegundo) {
        return almacenResultados[indicePrimero].getMoves()
                < almacenResultados[indiceSegundo].getMoves();
    }

    /**
     * Intercambia dos elementos del array que representa el heap.
     * Se usa durante subirElemento() y bajarElemento() para reordenar los nodos
     * y mantener la propiedad del Min-Heap.
     *
     * @param primerIndice  índice del primer elemento a intercambiar
     * @param segundoIndice índice del segundo elemento a intercambiar
     */
    private void intercambiar(int primerIndice, int segundoIndice) {
        GameResult guardaTemporal = almacenResultados[primerIndice];
        almacenResultados[primerIndice] = almacenResultados[segundoIndice];
        almacenResultados[segundoIndice] = guardaTemporal;
    }

    /**
     * Sube un elemento desde su posición actual hasta donde le corresponde
     * según la propiedad del Min-Heap (el padre siempre tiene menos movimientos que los hijos).
     *
     * Se usa justo después de insertar un nuevo elemento al final del array.
     * El nuevo elemento "sube" comparándose con su padre, y si tiene más prioridad
     * (menos movimientos), se intercambian. Repite hasta llegar a la raíz
     * o hasta que el padre ya tenga más prioridad.
     *
     * Ejemplo visual:
     *   Insertamos un resultado con 3 movimientos al final:
     *   [5, 8, 9, 3]  →  sube comparando con padre (9) → intercambia
     *   [5, 8, 3, 9]  →  sube comparando con padre (5) → no intercambia, terminó
     *
     * @param indiceElemento índice del elemento que debe subir
     */
    private void subirElemento(int indiceElemento) {
        while (indiceElemento > 0
                && tieneMayorPrioridad(indiceElemento, indicePadre(indiceElemento))) {
            intercambiar(indiceElemento, indicePadre(indiceElemento));
            indiceElemento = indicePadre(indiceElemento);
        }
    }

    /**
     * Baja un elemento desde su posición actual hasta donde le corresponde
     * según la propiedad del Min-Heap (el padre siempre tiene menos movimientos que los hijos).
     *
     * Se usa justo después de extraer la raíz: el último elemento del array
     * se pone en la raíz y luego "baja" comparándose con sus hijos.
     * Se intercambia con el hijo de mayor prioridad (menos movimientos)
     * hasta que ningún hijo tenga más prioridad o se llegue a una hoja.
     *
     * Ejemplo visual:
     *   Extraemos la raíz, ponemos el último elemento arriba:
     *   [9, 5, 8]  →  baja comparando con hijos (5 y 8) → intercambia con 5
     *   [5, 9, 8]  →  no tiene hijos, terminó
     *
     * @param indiceElemento índice del elemento que debe bajar
     */
    private void bajarElemento(int indiceElemento) {
        int indiceMenor = indiceElemento;
        int indiceIzquierdo = indiceHijoIzquierdo(indiceElemento);
        int indiceDerecho = indiceHijoDerecho(indiceElemento);

        // Verifica si el hijo izquierdo existe y tiene más prioridad que el nodo actual
        if (indiceIzquierdo < cantidadActual
                && tieneMayorPrioridad(indiceIzquierdo, indiceMenor)) {
            indiceMenor = indiceIzquierdo;
        }

        // Verifica si el hijo derecho existe y tiene más prioridad que el menor encontrado
        if (indiceDerecho < cantidadActual
                && tieneMayorPrioridad(indiceDerecho, indiceMenor)) {
            indiceMenor = indiceDerecho;
        }

        // Si algún hijo tiene más prioridad, intercambia y continúa bajando
        if (indiceMenor != indiceElemento) {
            intercambiar(indiceElemento, indiceMenor);
            bajarElemento(indiceMenor);
        }
    }

    /**
     * Encuentra el índice del resultado con MÁS movimientos dentro del heap.
     * Es decir, el PEOR resultado del top-K actual.
     *
     * En un Min-Heap, los peores resultados (más movimientos) siempre están
     * en las hojas, que ocupan la segunda mitad del array (desde size/2 hasta size-1).
     * Por eso solo se recorre esa mitad para encontrar el peor.
     *
     * Se usa en tryAdd() para decidir si un nuevo resultado merece entrar al top-K
     * reemplazando al peor existente.
     *
     * @return índice del resultado con más movimientos en el heap
     */
    private int encontrarIndicePeorResultado() {
        int indicePeor = cantidadActual / 2; // primera hoja del heap

        for (int i = cantidadActual / 2 + 1; i < cantidadActual; i++) {
            if (almacenResultados[i].getMoves() > almacenResultados[indicePeor].getMoves()) {
                indicePeor = i;
            }
        }
        return indicePeor;
    }

    // OPERACIONES PÚBLICAS

    /**
     * Inserta un nuevo resultado en la cola de prioridad.
     * El resultado se agrega al final del array y luego sube
     * hasta su posición correcta mediante subirElemento().
     * Si la cola está llena, el resultado se ignora silenciosamente.
     *
     * @param nuevoResultado el resultado de la partida a insertar
     */
    public void insert(GameResult nuevoResultado) {
        if (isFull()) return;
        almacenResultados[cantidadActual] = nuevoResultado;
        subirElemento(cantidadActual);
        cantidadActual++;
    }

    /**
     * Extrae y retorna el resultado con MENOS movimientos (la raíz del heap).
     * Después de extraer la raíz, el último elemento del array ocupa su lugar
     * y baja hasta su posición correcta mediante bajarElemento().
     * Si la cola está vacía, retorna null.
     *
     * @return el mejor resultado (menos movimientos), o null si la cola está vacía
     */
    public GameResult extractMin() {
        if (isEmpty()) return null;

        GameResult mejorResultado = almacenResultados[0];

        // El último elemento toma el lugar de la raíz extraída
        almacenResultados[0] = almacenResultados[cantidadActual - 1];
        almacenResultados[cantidadActual - 1] = null;
        cantidadActual--;

        // Si aún quedan elementos, reorganizar el heap bajando la nueva raíz
        if (!isEmpty()) {
            bajarElemento(0);
        }

        return mejorResultado;
    }

    /**
     * Retorna el mejor resultado (menos movimientos) SIN extraerlo de la cola.
     * Útil para consultar el top-1 sin modificar la estructura.
     * Si la cola está vacía, retorna null.
     *
     * @return el mejor resultado, o null si la cola está vacía
     */
    public GameResult peekMin() {
        return isEmpty() ? null : almacenResultados[0];
    }

    /**
     * Intenta agregar un resultado al top-K del leaderboard.
     *
     * Hay dos casos:
     *   1. La cola no está llena → se inserta directamente.
     *   2. La cola está llena → solo entra si tiene MENOS movimientos
     *      que el peor resultado actual del top-K. En ese caso, el peor
     *      es reemplazado y el heap se reorganiza.
     *
     * Este método es el punto de entrada principal para el leaderboard,
     * ya que maneja automáticamente la lógica de "¿merece entrar este resultado?".
     *
     * @param nuevoResultado el resultado de la partida recién terminada
     */
    public void tryAdd(GameResult nuevoResultado) {
        if (!isFull()) {
            insert(nuevoResultado);
        } else {
            int indicePeor = encontrarIndicePeorResultado();
            if (nuevoResultado.getMoves() < almacenResultados[indicePeor].getMoves()) {
                almacenResultados[indicePeor] = nuevoResultado;
                subirElemento(indicePeor);
                bajarElemento(indicePeor);
            }
        }
    }

    /**
     * Verifica si la cola está completamente vacía.
     *
     * @return true si no hay ningún resultado guardado
     */
    public boolean isEmpty() {
        return cantidadActual == 0;
    }

    /**
     * Verifica si la cola alcanzó su capacidad máxima (el K del top-K).
     *
     * @return true si la cola está llena y no puede recibir más inserciones directas
     */
    public boolean isFull() {
        return cantidadActual == capacidadMaxima;
    }

    /**
     * Retorna la cantidad de resultados actualmente guardados en la cola.
     *
     * @return número de elementos en el heap
     */
    public int getSize() {
        return cantidadActual;
    }

    /**
     * Genera una representación legible del contenido actual del heap.
     * Los resultados se muestran en el orden interno del array (no necesariamente
     * de mejor a peor — para eso usar printLeaderboard() en Leaderboard).
     *
     * @return cadena con todos los resultados numerados
     */
    @Override
    public String toString() {
        StringBuilder contenidoCola = new StringBuilder("PriorityQueue [\n");
        for (int i = 0; i < cantidadActual; i++) {
            contenidoCola.append("  ")
                    .append(i + 1)
                    .append(". ")
                    .append(almacenResultados[i].toString())
                    .append("\n");
        }
        contenidoCola.append("]");
        return contenidoCola.toString();
    }
}