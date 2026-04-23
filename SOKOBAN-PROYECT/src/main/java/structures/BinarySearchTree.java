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

}