package model;

import structures.PriorityQueue;

/**
 * Leaderboard por nivel usando una cola de prioridad.
 * Mantiene el top-K de mejores resultados (menos movimientos) por nivel.
 * Cuando llega un nuevo resultado, automáticamente decide si entra al top-K
 * o se descarta, comparándolo contra el peor resultado actual.
 */
public class Leaderboard {

    private static final int TOP_K = 5;
    private PriorityQueue mejoresResultados;
    private int levelId;

    /**
     * Constructor: inicializa el leaderboard para un nivel específico.
     * Crea una cola de prioridad vacía con capacidad para el top-K.
     *
     * @param levelId id del nivel al que pertenece este leaderboard
     */
    public Leaderboard(int levelId) {
        this.levelId = levelId;
        this.mejoresResultados = new PriorityQueue(TOP_K);
    }

    /**
     * Intenta agregar un nuevo resultado al top-K del leaderboard.
     * Delega completamente en tryAdd() de la cola de prioridad, que internamente
     * decide si el resultado es suficientemente bueno para entrar al top-K
     * o si simplemente se descarta porque hay mejores resultados ya guardados.
     *
     * @param resultado el resultado de la partida recién terminada
     */
    public void addResult(GameResult resultado) {
        mejoresResultados.tryAdd(resultado);
    }

    /**
     * Imprime en consola el leaderboard ordenado de mejor a peor resultado.
     *
     * Como la cola de prioridad no tiene recorrido directo sin destruirse,
     * el proceso es:
     *   1. Extraer todos los resultados a un arreglo temporal (copiaTemporalResultados).
     *   2. Imprimir cada resultado con su posición en el ranking.
     *   3. Reinsertar todos de vuelta a la cola para no perder los datos.
     */
    public void printLeaderboard() {
        System.out.println("=== Leaderboard - Level " + levelId + " ===");

        // Arreglo temporal para guardar los resultados mientras vaciamos la cola
        // sin perder los datos permanentemente
        GameResult[] copiaTemporalResultados = new GameResult[mejoresResultados.getSize()];
        int cantidadGuardada = 0;

        // Paso 1: extraer todos los resultados de la cola al arreglo temporal
        while (!mejoresResultados.isEmpty()) {
            copiaTemporalResultados[cantidadGuardada++] = mejoresResultados.extractMin();
        }

        // Paso 2: imprimir cada resultado con su posición en el ranking
        // y al mismo tiempo reinsertarlo en la cola para no perder los datos
        for (int i = 0; i < cantidadGuardada; i++) {
            System.out.println((i + 1) + ". " + copiaTemporalResultados[i].toString());
            mejoresResultados.insert(copiaTemporalResultados[i]);
        }
    }

    /**
     * Retorna el id del nivel al que pertenece este leaderboard.
     * Útil para identificar a qué nivel corresponden los resultados mostrados.
     *
     * @return id del nivel
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * Retorna la cola de prioridad con los mejores resultados.
     * Útil si desde otra clase (como GameController) se necesita
     * acceder directamente a los resultados guardados.
     *
     * @return cola de prioridad con el top-K de resultados
     */
    public PriorityQueue getMejoresResultados() {
        return mejoresResultados;
    }
}