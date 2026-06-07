package model;

public class FloydWarshall {


    public static final int INF = Integer.MAX_VALUE / 2;

    private int rows;
    private int cols;

    /**
     * Matriz de distancias minimas.
     * dist[i][j] = minima cantidad de pasos del nodo i al nodo j.
     * Si dist[i][j] == INF no existe camino entre ellos.
     */
    private int[][] dist;

    /**
     * Construye la matriz Floyd-Warshall para el tablero dado.
     * Despues de este constructor, dist[i][j] esta listo para consultas O(1).
     *
     * @param board Tablero del juego.
     */
    public FloydWarshall(Board board) {
        this.rows = board.getWidth();
        this.cols = board.getHeight();
        int V = rows * cols;

        dist = new int[V][V];

        //Inicializa la matriz con diagonal en 0s y el resto INF
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = INF;
                }
            }
        }

        //Posibilidades de moverse en x & y
        // Índice 0 arriba: x-1, y sin cambio
        //Índice 1 abajo: x+1, y sin cambio
        //Índice 2 izquierda: x sin cambio, y-1
        //Índice 3 derecha: x sin cambio, y+1
        int[] dx = {-1, 1,  0, 0};
        int[] dy = { 0, 0, -1, 1};

        //Este for lo que hace es verificar que una celda sea caminable, si lo es lo que hace es
        //Sacar el indice de la celda
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {

                // Si esta celda no es caminable, no es nodo del grafo
                Cell cell = board.getCell(x, y);
                if (cell == null || !cell.isWalkable()) {
                    continue;
                }

                int cellId = x * cols + y;
            //Hasta aqui.
                //Este for lo que hace es verificar que las celdas vecinas sean caminables y esten dentro
                //del tablero y si cumple las verificaciones pues añade una arista bidireccional
                //ya que se conectan entre ellas y en Sokoban se puede moverse en varias direcciones
                for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    // Verificar que el vecino este dentro del tablero
                    if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) {
                        continue;
                    }

                    // Si el vecino no es caminable, tampoco es nodo
                    Cell neighbor = board.getCell(nx, ny);
                    if (neighbor == null || !neighbor.isWalkable()) {
                        continue;
                    }

                    int neighborId = nx * cols + ny;

                    // Arista de peso 1 en ambas direcciones
                    dist[cellId][neighborId] = 1;
                    dist[neighborId][cellId] = 1;
                }
            }
        }

        /*
        * k es el nodo intermedio. k / cols y k % cols convierten el índice lineal de vuelta a coordenadas 2D
        * la operación inversa de cellId = x * cols + y.
        * Si k es un muro, no puede ser intermediario de ningún camino, se salta.*/
        for (int k = 0; k < V; k++) {

            // Si k corresponde a una celda no caminable, no sirve como intermediario
            int kx = k / cols;
            int ky = k % cols;
            Cell kCell = board.getCell(kx, ky);
            if (kCell == null || !kCell.isWalkable()) {
                continue;
            }

            /*
            * Para cada par (i, j) pregunta: "¿ir de i a k y luego de k a j es más corto que
            * el mejor camino conocido de i a j?" Si sí, actualiza.
            * El if (dist[i][k] == INF) continue es una optimización
            * si no hay camino de i a k, no tiene sentido revisar ningún j.
            * El if (dist[k][j] != INF) evita sumar INF y desbordar.
             */
            for (int i = 0; i < V; i++) {
                if (dist[i][k] == INF) {
                    continue;
                }

                for (int j = 0; j < V; j++) {

                    // Es mas corto ir i->k->j que el mejor camino i->j conocido?
                    if (dist[k][j] != INF) {
                        int candidato = dist[i][k] + dist[k][j];
                        if (candidato < dist[i][j]) {
                            dist[i][j] = candidato;
                        }
                    }
                }
            }
        }
    }



    /**
     * Distancia minima entre dos posiciones del tablero.
     * Usa getX() y getY() igual que el resto del proyecto.
     *
     * @param from Posicion origen.
     * @param to   Posicion destino.
     * @return     Pasos minimos, o INF si no hay camino.
     */
    /*
    * Convierte las dos posiciones a índices lineales y
    * accede directamente a la matriz. O(1). No hace ningún cálculo
    * */
    public int getDistance(Position from, Position to) {
        int idFrom = from.getX() * cols + from.getY();
        int idTo   = to.getX()   * cols + to.getY();
        return dist[idFrom][idTo];
    }

    /**
     * Distancia minima usando coordenadas directas.
     * @param x1 Coordenada X origen.
     * @param y1 Coordenada Y origen.
     * @param x2 Coordenada X destino.
     * @param y2 Coordenada Y destino.
     * @return   Pasos minimos, o INF si no hay camino.
     */

    /*
    * Misma logica que el de arriba pero sin recibir positions
    * solo coordenadas exactas
    * */
    public int getDistance(int x1, int y1, int x2, int y2) {
        int idFrom = x1 * cols + y1;
        int idTo   = x2 * cols + y2;
        return dist[idFrom][idTo];
    }

    /**
     * @param boxPositions  Array con las posiciones actuales de las cajas.
     * @param boxCount      Cantidad de cajas en el array.
     * @param goalPositions Array con las posiciones de los objetivos.
     * @param goalCount     Cantidad de objetivos en el array.
     * @return              Suma de distancias minimas, o INF si hay caja atrapada.
     * Complejidad: O(B * G) donde B = cajas, G = objetivos.
     */

    /*
    * Para cada caja itera todos los objetivos buscando el más cercano. minDist arranca en INF
    * y se actualiza cada vez que encuentra una distancia menor
    * Si al terminar el loop interno minDist sigue siendo INF, esa caja no puede llegar a ningún objetivo
    * retorna INF inmediatamente sin revisar las demás cajas. Si encuentra distancia, la suma al total
    * El resultado es la suma de "distancia mínima de cada caja a su objetivo más cercano"
    * */
    public int computeHeuristic(Position[] boxPositions, int boxCount,
                                Position[] goalPositions, int goalCount) {
        int total = 0;

        for (int b = 0; b < boxCount; b++) {
            int minDist = INF;

            for (int g = 0; g < goalCount; g++) {
                int d = getDistance(boxPositions[b], goalPositions[g]);
                if (d < minDist) {
                    minDist = d;
                }
            }

            // Caja que no puede llegar a ningun objetivo -> estado sin solucion
            if (minDist == INF) {
                return INF;
            }

            total += minDist;
        }

        return total;
    }

    /** Retorna la matriz completa (util para debug o visualizacion). */
    public int[][] getDistMatrix() {
        return dist;
    }
}