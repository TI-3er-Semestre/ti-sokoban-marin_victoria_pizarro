package model;

public class Dijkstra {

    public static final int INF = Integer.MAX_VALUE / 2;

    private Board board;
    private int rows;
    private int cols;

    public Dijkstra(Board board) {
        this.board = board;
        this.rows = board.getWidth();
        this.cols = board.getHeight();
    }
    public int shortestDistance(Position start, Position goal){
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

        }
    }

}