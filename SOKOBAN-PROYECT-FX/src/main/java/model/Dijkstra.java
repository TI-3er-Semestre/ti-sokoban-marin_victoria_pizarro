package model;

public class Dijkstra {

    public static final int INF = Integer.MAX_VALUE / 2;

    private Board board;
    private int rows;
    private int cols;
    private void processNeighbor(int currentId, int neighborId,
            int[] dist,
            boolean[] visited) {

        if (visited[neighborId]) {
            return;
        }

        int newDistance = dist[currentId] + 1;

        if (newDistance < dist[neighborId]) {
            dist[neighborId] = newDistance;
        }
    }


    public Dijkstra(Board board) {
        this.board = board;
        this.rows = board.getWidth();
        this.cols = board.getHeight();
    }
    private boolean isValidAndWalkable(int x, int y) {
        Cell cell = board.getCell(x, y);
        return cell != null && cell.isWalkable();
    }


    public int shortestDistance(Position start, Position goal){
        if (start == null  || goal == null){
            return INF;
        }

        int totalNodes = rows * cols;
        int[] dist = new int[totalNodes];
        boolean[] visited = new boolean[totalNodes];

        for (int i = 0; i < totalNodes; i ++){
            dist[i] = INF;
            visited[i] = false;
        }
        int startId = start.getX() * cols + start.getY();
        int goalId = goal.getX() * cols + goal.getY();
        dist[startId] = 0;
        while (true){
            int current = -1;
            int minDistance = INF;
            for (int i = 0; i< totalNodes; i++){
                if(!visited[i] && dist[i]< minDistance){
                    minDistance = dist[i];
                    current = i;
                }
            }
            if (current == -1){
                break;
            }
            if (current == goalId){
                return dist[current];
            }
            visited[current] = true;
            int x = current / cols;
            int y = current % cols;


        }
    }

}