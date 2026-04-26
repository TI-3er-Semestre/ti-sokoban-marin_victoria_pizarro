package model;

import structures.LinkedList;
import structures.PriorityQueue;

/**
 * Leaderboard del juego que combina dos responsabilidades:
 *
 * 1. HISTORIAL COMPLETO (LinkedList + MergeSort):
 *    Guarda todas las partidas jugadas en una lista enlazada y permite
 *    ordenarlas por distintos criterios usando MergeSort.
 *    Usado para la pantalla de estadísticas generales.
 *
 * 2. TOP-K (PriorityQueue):
 *    Mantiene los mejores K resultados por nivel usando una cola de prioridad
 *    (Min-Heap). Solo entran al top-K los resultados con menos movimientos.
 *    Usado para el ranking rápido por nivel.
 */
public class Leaderboard {

    // ── Top-K con cola de prioridad ───────────────────────────────────────────
    private static final int TOP_K = 5;
    private PriorityQueue bestResults;
    private int levelId;

    // ── Historial completo con lista enlazada ─────────────────────────────────
    private LinkedList<GameStats> fullHistory;

    /**
     * Constructor: inicializa el leaderboard para un nivel específico.
     * Crea tanto la cola de prioridad para el top-K como la lista
     * enlazada para el historial completo de partidas.
     *
     * @param levelId id del nivel al que pertenece este leaderboard
     */
    public Leaderboard(int levelId) {
        this.levelId = levelId;
        this.bestResults = new PriorityQueue(TOP_K);
        this.fullHistory = new LinkedList<>();
    }

    // MÉTODOS DEL TOP-K (PriorityQueue)
    /**
     * Intenta agregar un nuevo resultado al top-K del leaderboard.
     * Delega en tryAdd() de la cola de prioridad, que internamente decide
     * si el resultado merece entrar al top-K o se descarta.
     *
     * @param resultado el resultado de la partida recién terminada
     */
    public void addResult(GameResult result) {
        bestResults.tryAdd(result);
    }

    /**
     * Imprime en consola el top-K ordenado de mejor a peor resultado.
     * Extrae los resultados a un arreglo temporal para no destruir la cola,
     * los imprime y los reinserta.
     */
    public void printTopK() {
        System.out.println("=== Top-" + TOP_K + " Leaderboard - Level " + levelId + " ===");

        // Arreglo temporal para no perder los datos al vaciar la cola
        GameResult[] tempResultsCopy = new GameResult[bestResults.getSize()];
        int savedCount = 0;

        // Paso 1: extraer todos a arreglo temporal
        while (!bestResults.isEmpty()) {
            tempResultsCopy[savedCount++] = bestResults.extractMin();
        }

        // Paso 2: imprimir y reinsertar
        for (int i = 0; i < savedCount; i++) {
            System.out.println((i + 1) + ". " + tempResultsCopy[i].toString());
            bestResults.insert(tempResultsCopy[i]);
        }
    }

    /**
     * Retorna la cola de prioridad con los mejores resultados.
     *
     * @return cola de prioridad con el top-K de resultados
     */
    public PriorityQueue getBestResults() {
        return bestResults;
    }

    // MÉTODOS DEL HISTORIAL COMPLETO (LinkedList + MergeSort)

    /**
     * Agrega una nueva estadística al historial completo de partidas.
     * Se guarda en la lista enlazada sin importar si entra al top-K o no.
     *
     * @param stat la estadística de la partida a agregar
     */
    public void addStats(GameStats stat) {
        fullHistory.add(stat);
    }

    /**
     * Retorna la lista enlazada con el historial completo de partidas.
     *
     * @return lista enlazada de GameStats
     */
    public LinkedList<GameStats> getFullHistory() {
        return fullHistory;
    }

    /**
     * Muestra el historial completo de partidas en consola.
     */
    public void displayHistorial() {
        System.out.println("=== Historial Completo - Level " + levelId + " ===");
        System.out.println(fullHistory.display());
    }

    /**
     * Convierte la lista enlazada interna a un arreglo de GameStats.
     * Usado internamente por los métodos de ordenamiento.
     *
     * @return arreglo con todos los elementos del historial
     */
    private GameStats[] toArray() {
        GameStats[] arr = new GameStats[fullHistory.getLength()];
        int i = 0;
        structures.CustomIterator<GameStats> it = fullHistory.iterator();
        while (it.hasNext()) {
            arr[i++] = it.next().getData();
        }
        return arr;
    }

    /**
     * Ordena el historial por número de movimientos (ascendente).
     * @return arreglo ordenado de GameStats
     */
    public GameStats[] sortByMoves() {
        GameStats[] arr = toArray();
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * Ordena el historial por nivel (ascendente).
     * @return arreglo ordenado de GameStats
     */
    public GameStats[] sortByLevel() {
        GameStats[] arr = toArray();
        mergeSortByLevel(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * Ordena el historial por puntaje (descendente, mayor primero).
     * @return arreglo ordenado de GameStats
     */
    public GameStats[] sortByScore() {
        GameStats[] arr = toArray();
        mergeSortByScore(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * Ordena el historial por empujes (ascendente).
     * @return arreglo ordenado de GameStats
     */
    public GameStats[] sortByPushes() {
        GameStats[] arr = toArray();
        mergeSortByPushes(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * Ordena el historial por tiempo (ascendente).
     * @return arreglo ordenado de GameStats
     */
    public GameStats[] sortByTime() {
        GameStats[] arr = toArray();
        mergeSortByTime(arr, 0, arr.length - 1);
        return arr;
    }

    // =========================================================================
    // MÉTODOS PRIVADOS DE MERGESORT
    // =========================================================================

    /**
     * ordena un subarreglo por numero de movimeintos, usando merge sort
     * @param arr arreglo de estadisticas de partida
     * @param left indice izquierdo del subarreglo
     * @param right indice derecho del subarreglo
     */
    private void mergeSort(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    /**
     * combina 2 subarreglos adyacentes ordenados por moviemiento en uno solo ordenado
     * @param arr arreglo que contienen los subarreglos
     * @param left limite izquierdo del primer subarreglo
     * @param mid limite derecho del primer subarreg;p
     * @param right limite derecho del segundo subarreglo
     */
    private void merge(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getMoves() <= R[j].getMoves()) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    private void mergeSortByLevel(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByLevel(arr, left, mid);
            mergeSortByLevel(arr, mid + 1, right);
            mergeByLevel(arr, left, mid, right);
        }
    }

    /**
     * combina 2 subareeglos ordenados por nivel en uno solo ordenado
     *
     * @param arr arreglo
     * @param left inicio del primer subarreglo
     * @param right fun del segundo subarreglo
     */
    private void mergeByLevel(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getLevelId() <= R[j].getLevelId()) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    /**
     *  ordena un subarreglo por puntake( mayor a menor) usando Merge sort
     * @param arr arreglo
     * @param left izquierda
     * @param right derecha
     */
    private void mergeSortByScore(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByScore(arr, left, mid);
            mergeSortByScore(arr, mid + 1, right);
            mergeByScore(arr, left, mid, right);
        }
    }

    /**
     * combina 2 subarreglos ordenados por puntaje(decendente) en uno solo
     * @param arr arreglo
     * @param left indice izquierdo
     * @param mid punto medio
     * @param right limite derecho
     */
    private void mergeByScore(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].calculateScore() >= R[j].calculateScore()) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    /**
     *  ordena un subarreglo por numero de empujes, menor a mayor
     * @param arr arreglo
     * @param left izquierda
     * @param right derecha
     */
    private void mergeSortByPushes(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByPushes(arr, left, mid);
            mergeSortByPushes(arr, mid + 1, right);
            mergeByPushes(arr, left, mid, right);
        }
    }

    /**
     * combina 2 subarreglos ordenados por empujes,
     * @param arr arreglo
     * @param left izquierda
     * @param mid medio
     * @param right derecha
     */
    private void mergeByPushes(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getPushes() <= R[j].getPushes()) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    /**
     * ordena un subarreglo por tiempo, menor a mayor
     * @param arr arreglo
     * @param left izquierda
     * @param right derecha
     */
    private void mergeSortByTime(GameStats[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByTime(arr, left, mid);
            mergeSortByTime(arr, mid + 1, right);
            mergeByTime(arr, left, mid, right);
        }
    }

    /**
     * combina 2 subarreglos ordenados por tiempo
     * @param arr arreglo
     * @param left izquierda
     * @param mid medio
     * @param right derecha
     */
    private void mergeByTime(GameStats[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        GameStats[] L = new GameStats[n1];
        GameStats[] R = new GameStats[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].getTime() <= R[j].getTime()) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // binarios traidos desde la rama busqueda binaria

    /**
     * busca una estadistica, cuyo porcentaje sea aprox igual al valor dado
     * @param score puntaje a buscar
     * @return el GameStats con puntaje aproximado
     */
    public GameStats binarySearchByScore(double score) {
        GameStats[] arr = sortByScore();

        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            double midScore = arr[mid].calculateScore();

            if (Math.abs(midScore - score) < 0.0001)
                return arr[mid];

            if (midScore < score)
                right = mid - 1;
            else
                left = mid + 1;
        }

        return null;
    }

    /**
     * busca una estadistica con tiempo exactamente igual al valo dado
     * @param time tiempo a buscar
     * @return GameStats con getTime() == time, o null si no existe
     */
    public GameStats binarySearchByTime(double time) {
        GameStats[] arr = sortByTime();
        // bisqueda binaria por tiempo
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid].getTime() == time)
                return arr[mid];

            if (arr[mid].getTime() < time)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return null;
    }

    /**
     * busca una estadistica con identificador de nivel exactamente igual
     * @param levelId identificador del nivel a buscar
     * @return GameStats con getLevelId() == levelId, o null si no existe
     */
    public GameStats binarySearchByLevel(int levelId) {
        GameStats[] arr = sortByLevel();
        // busqueda binaria por nivel

        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid].getLevelId() == levelId)
                return arr[mid];

            if (arr[mid].getLevelId() < levelId)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return null;
    }

    // =========================================================================
    // GETTERS GENERALES
    // =========================================================================

    /**
     * Retorna el id del nivel al que pertenece este leaderboard.
     *
     * @return id del nivel
     */
    public int getLevelId() {
        return levelId;
    }
}