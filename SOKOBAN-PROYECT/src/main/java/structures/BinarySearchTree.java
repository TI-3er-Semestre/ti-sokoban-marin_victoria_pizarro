package structures;

/**
 * Árbol Binario de Búsqueda genérico implementado con el patrón Fachada.
 *
 * Los métodos públicos actúan como la "fachada" — puntos de entrada simples
 * y limpios que delegan toda la lógica recursiva a métodos privados auxiliares.
 *
 * T debe implementar Comparable para que los elementos puedan ordenarse
 * dentro del árbol.
 *
 * @param <T> el tipo de elementos almacenados, debe ser Comparable
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private BSTNode<T> root;
    private int totalNodes;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public BinarySearchTree() {
        this.root = null;
        this.totalNodes = 0;
    }
    //Getters

    public BSTNode<T> getRoot() {
        return root;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    /**
    * Metodos fachada usando el patrón fachada*/
    /**
     * Inserta un nuevo valor en el árbol.
     *
     * @param data el valor a insertar
     * @return true si se insertó correctamente, false si el valor ya existía
     */
    public boolean addNode(T data) {
        if (searchNode(data) != null) {
            return false; // no se permiten duplicados
        }
        root = recursiveAdd(root, data);
        totalNodes++;
        return true;
    }

    /**
     * Elimina un valor del árbol.
     *
     * @param data el valor a eliminar
     * @return true si se eliminó correctamente, false si el valor no existía
     */
    public boolean deleteNode(T data) {
        if (searchNode(data) == null) {
            return false;
        }
        root = recursiveDelete(root, data);
        totalNodes--;
        return true;
    }

    /**
     * Busca un valor en el árbol.
     *
     * @param data el valor a buscar
     * @return el dato si fue encontrado, null en caso contrario
     */
    public T searchNode(T data) {
        BSTNode<T> resultado = recursiveSearch(root, data);
        return resultado != null ? resultado.getData() : null;
    }

    /**
     * @return el valor más grande del árbol, o null si está vacío
     */
    public T max() {
        if (root == null) return null;
        return recursiveMax(root).getData();
    }

    /**
     * @return el valor más pequeño del árbol, o null si está vacío
     */
    public T min() {
        if (root == null) return null;
        return recursiveMin(root).getData();
    }

    /**
     * Retorna el recorrido en pre-orden (raíz → izquierda → derecha).
     *
     * @return cadena de valores separados por espacio en pre-orden
     */
    public String preorder() {
        StringBuilder resultado = new StringBuilder();
        recursivePreorder(root, resultado);
        return resultado.toString().trim();
    }

    /**
     * Retorna el recorrido en in-orden (izquierda → raíz → derecha).
     * En un BST, esto entrega los valores en orden ascendente.
     *
     * @return cadena de valores separados por espacio en in-orden
     */
    public String inorder() {
        StringBuilder resultado = new StringBuilder();
        recursiveInorder(root, resultado);
        return resultado.toString().trim();
    }

    /**
     * Retorna el recorrido en post-orden (izquierda → derecha → raíz).
     *
     * @return cadena de valores separados por espacio en post-orden
     */
    public String posorder() {
        StringBuilder resultado = new StringBuilder();
        recursivePostorder(root, resultado);
        return resultado.toString().trim();
    }

    /**
     * Retorna la profundidad del nodo que contiene el valor dado.
     * La profundidad de la raíz = 0.
     *
     * @param data el valor a localizar
     * @return profundidad del nodo, o -1 si el valor no fue encontrado
     */
    public int depth(T data) {
        return recursiveDepth(root, data, 0);
    }

    /**
     * Retorna la altura del nodo que contiene el valor dado.
     * La altura de un nodo hoja = 0.
     *
     * @param data el valor a localizar
     * @return altura del nodo, o -1 si el valor no fue encontrado
     */
    public int height(T data) {
        return recursiveHeight(root, data);
    }

    /**
     * Retorna una cadena con todos los ancestros del nodo con el valor dado,
     * desde la raíz hacia abajo (sin incluir el nodo mismo).
     *
     * @param data el valor cuyos ancestros se buscan
     * @return ancestros separados por espacio, o cadena vacía si es raíz o no existe
     */
    public String ascendants(T data) {
        StringBuilder resultado = new StringBuilder();
        recursiveAscendants(root, data, resultado);
        return resultado.toString().trim();
    }

    /**
     * Retorna una cadena con todos los descendientes del nodo con el valor dado
     * (todo el subárbol por debajo de ese nodo, en in-orden).
     *
     * @param data el valor cuyos descendientes se buscan
     * @return descendientes separados por espacio, o cadena vacía si no existe o es hoja
     */
    public String descendants(T data) {
        BSTNode<T> nodoObjetivo = recursiveSearch(root, data);
        if (nodoObjetivo == null) return "";
        StringBuilder resultado = new StringBuilder();
        collectDescendants(nodoObjetivo, resultado);
        return resultado.toString().trim();
    }

    // =========================================================================
    // MÉTODOS PRIVADOS RECURSIVOS
    // =========================================================================

    /**
     * Inserta el dato de forma recursiva y retorna la raíz del subárbol actualizado.
     */
    private BSTNode<T> recursiveAdd(BSTNode<T> nodoActual, T data) {
        // Caso base: se encontró un espacio vacío, se crea el nuevo nodo aquí
        if (nodoActual == null) {
            return new BSTNode<>(data);
        }
        int comparacion = data.compareTo(nodoActual.getData());
        if (comparacion < 0) {
            // Caso recursivo 1: el dato es menor, se busca posición en la izquierda
            nodoActual.setLeft(recursiveAdd(nodoActual.getLeft(), data));
        } else if (comparacion > 0) {
            // Caso recursivo 2: el dato es mayor, se busca posición en la derecha
            nodoActual.setRight(recursiveAdd(nodoActual.getRight(), data));
        }
        return nodoActual;
    }

    /**
     * Elimina el dato de forma recursiva y retorna la raíz del subárbol actualizado.
     */
    private BSTNode<T> recursiveDelete(BSTNode<T> nodoActual, T data) {
        // Caso base: subárbol vacío, no hay nada que eliminar
        if (nodoActual == null) return null;

        int comparacion = data.compareTo(nodoActual.getData());

        if (comparacion < 0) {
            // El dato está en el subárbol izquierdo
            nodoActual.setLeft(recursiveDelete(nodoActual.getLeft(), data));
        } else if (comparacion > 0) {
            // El dato está en el subárbol derecho
            nodoActual.setRight(recursiveDelete(nodoActual.getRight(), data));
        } else {
            // Nodo encontrado — tres casos de eliminación:

            // Caso 1: nodo hoja o con solo hijo derecho → se sube el hijo derecho
            if (nodoActual.getLeft() == null) return nodoActual.getRight();

            // Caso 2: nodo con solo hijo izquierdo → se sube el hijo izquierdo
            if (nodoActual.getRight() == null) return nodoActual.getLeft();

            // Caso 3: nodo con dos hijos → se reemplaza con el sucesor en in-orden
            // (el mínimo del subárbol derecho) para mantener la propiedad del BST
            BSTNode<T> sucesor = recursiveMin(nodoActual.getRight());
            nodoActual.setData(sucesor.getData());
            nodoActual.setRight(recursiveDelete(nodoActual.getRight(), sucesor.getData()));
        }
        return nodoActual;
    }

    /**
     * Busca recursivamente un nodo que contenga el dato.
     * Retorna el nodo si lo encuentra, null si no existe.
     */
    private BSTNode<T> recursiveSearch(BSTNode<T> nodoActual, T data) {
        // Caso base: subárbol vacío, el dato no existe
        if (nodoActual == null) return null;

        int comparacion = data.compareTo(nodoActual.getData());

        if (comparacion == 0) return nodoActual;                                      // encontrado
        if (comparacion < 0) return recursiveSearch(nodoActual.getLeft(), data);      // buscar izquierda
        return recursiveSearch(nodoActual.getRight(), data);                           // buscar derecha
    }

    /**
     * Encuentra recursivamente el nodo con el valor máximo en un subárbol.
     * El máximo siempre está en el extremo más a la derecha.
     */
    private BSTNode<T> recursiveMax(BSTNode<T> nodoActual) {
        // Caso base: no hay nodo a la derecha, este es el máximo
        if (nodoActual.getRight() == null) return nodoActual;
        return recursiveMax(nodoActual.getRight());
    }

    /**
     * Encuentra recursivamente el nodo con el valor mínimo en un subárbol.
     * El mínimo siempre está en el extremo más a la izquierda.
     */
    private BSTNode<T> recursiveMin(BSTNode<T> nodoActual) {
        // Caso base: no hay nodo a la izquierda, este es el mínimo
        if (nodoActual.getLeft() == null) return nodoActual;
        return recursiveMin(nodoActual.getLeft());
    }

    /** Construye la cadena de recorrido pre-orden de forma recursiva. */
    private void recursivePreorder(BSTNode<T> nodoActual, StringBuilder resultado) {
        if (nodoActual == null) return;
        resultado.append(nodoActual.getData()).append(" "); // visitar raíz primero
        recursivePreorder(nodoActual.getLeft(), resultado);  // luego subárbol izquierdo
        recursivePreorder(nodoActual.getRight(), resultado); // luego subárbol derecho
    }

    /** Construye la cadena de recorrido in-orden de forma recursiva. */
    private void recursiveInorder(BSTNode<T> nodoActual, StringBuilder resultado) {
        if (nodoActual == null) return;
        recursiveInorder(nodoActual.getLeft(), resultado);   // primero subárbol izquierdo
        resultado.append(nodoActual.getData()).append(" ");  // luego visitar raíz
        recursiveInorder(nodoActual.getRight(), resultado);  // luego subárbol derecho
    }

    /** Construye la cadena de recorrido post-orden de forma recursiva. */
    private void recursivePostorder(BSTNode<T> nodoActual, StringBuilder resultado) {
        if (nodoActual == null) return;
        recursivePostorder(nodoActual.getLeft(), resultado);  // primero subárbol izquierdo
        recursivePostorder(nodoActual.getRight(), resultado); // luego subárbol derecho
        resultado.append(nodoActual.getData()).append(" ");   // visitar raíz al final
    }

    /**
     * Recorre el árbol buscando el dato y lleva la cuenta de la profundidad acumulada.
     * Retorna -1 si el valor no fue encontrado.
     */
    private int recursiveDepth(BSTNode<T> nodoActual, T data, int profundidadActual) {
        // Caso base: nodo vacío, el dato no existe en este camino
        if (nodoActual == null) return -1;

        int comparacion = data.compareTo(nodoActual.getData());

        if (comparacion == 0) return profundidadActual; // encontrado, retorna profundidad acumulada
        if (comparacion < 0) return recursiveDepth(nodoActual.getLeft(), data, profundidadActual + 1);
        return recursiveDepth(nodoActual.getRight(), data, profundidadActual + 1);
    }

    /**
     * Primero ubica el nodo que contiene el dato, luego delega el cálculo
     * de su altura al método auxiliar calculateSubtreeHeight.
     * Retorna -1 si el valor no fue encontrado.
     */
    private int recursiveHeight(BSTNode<T> nodoActual, T data) {
        // Caso base: subárbol vacío, el dato no existe
        if (nodoActual == null) return -1;

        int comparacion = data.compareTo(nodoActual.getData());

        if (comparacion < 0) return recursiveHeight(nodoActual.getLeft(), data);  // buscar izquierda
        if (comparacion > 0) return recursiveHeight(nodoActual.getRight(), data); // buscar derecha

        // Nodo encontrado — calcular su altura real
        return calculateSubtreeHeight(nodoActual);
    }

    /**
     * Calcula la altura del subárbol cuya raíz es el nodo dado.
     * La altura es el camino más largo desde el nodo hasta una hoja.
     * Altura de un nodo hoja = 0. Altura de null = -1.
     */
    private int calculateSubtreeHeight(BSTNode<T> nodoActual) {
        // Caso base: nodo vacío
        if (nodoActual == null) return -1;

        // Caso base: nodo hoja (sin hijos)
        if (nodoActual.getLeft() == null && nodoActual.getRight() == null) return 0;

        // Caso recursivo: la altura es 1 + el mayor camino entre los dos subárboles
        int alturaIzquierda = calculateSubtreeHeight(nodoActual.getLeft());
        int alturaDerecha   = calculateSubtreeHeight(nodoActual.getRight());
        return 1 + Math.max(alturaIzquierda, alturaDerecha);
    }

    /**
     * Recorre el camino hacia el dato acumulando los ancestros en el StringBuilder.
     * No agrega el nodo mismo cuando lo encuentra.
     * Retorna true si el dato fue encontrado en este subárbol (para el backtracking).
     */
    private boolean recursiveAscendants(BSTNode<T> nodoActual, T data, StringBuilder resultado) {
        // Caso base: subárbol vacío, el dato no existe por este camino
        if (nodoActual == null) return false;

        int comparacion = data.compareTo(nodoActual.getData());

        // Caso base: nodo encontrado — no se agrega a sí mismo como ancestro
        if (comparacion == 0) return true;

        // Agregar nodo actual como posible ancestro y continuar la búsqueda
        resultado.append(nodoActual.getData()).append(" ");

        if (comparacion < 0) {
            if (recursiveAscendants(nodoActual.getLeft(), data, resultado)) return true;
        } else {
            if (recursiveAscendants(nodoActual.getRight(), data, resultado)) return true;
        }

        // Retroceso (backtracking): si el dato no estaba por este camino,
        // se elimina este nodo del resultado porque no es realmente un ancestro
        String valorNodoActual = nodoActual.getData().toString();
        int indice = resultado.lastIndexOf(valorNodoActual);
        if (indice >= 0) resultado.delete(indice, indice + valorNodoActual.length() + 1); // +1 por el espacio
        return false;
    }

    /**
     * Recolecta todos los valores del subárbol dado usando in-orden,
     * excluyendo la raíz del subárbol (solo se recorren sus hijos).
     * Esos valores son los descendientes del nodo objetivo.
     */
    private void collectDescendants(BSTNode<T> nodoRaiz, StringBuilder resultado) {
        if (nodoRaiz == null) return;
        // Se recorren solo los hijos, sin incluir la raíz misma
        recursiveInorder(nodoRaiz.getLeft(), resultado);
        recursiveInorder(nodoRaiz.getRight(), resultado);
    }

    // =========================================================================
    // toString
    // =========================================================================

    @Override
    public String toString() {
        return "BinarySearchTree { totalNodes=" + totalNodes
                + ", root=" + (root != null ? root.getData() : "null")
                + ", inorder=[" + inorder() + "] }";
    }


}