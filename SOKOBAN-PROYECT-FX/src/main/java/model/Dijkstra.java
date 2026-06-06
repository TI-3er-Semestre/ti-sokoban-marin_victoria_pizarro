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

    private int getNodeId(int x, int y) {
        return x * cols + y;
    }

    private int getMinDistanceNode(int[] dist, boolean[] visited) {
        int minDistance = INF;
        int minNode = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < minDistance) {
                minDistance = dist[i];
                minNode = i;
            }
        }
        return minNode;
    }


    public int shortestDistance(Position start, Position goal) {
        if (start == null || goal == null) {
            return INF;
        }
        if (!isValidAndWalkable(start.getX(), start.getY()) || !isValidAndWalkable(goal.getX(), goal.getY())) {
            return INF;
        }

        int totalNodes = rows * cols;
        int[] dist = new int[totalNodes];
        boolean[] visited = new boolean[totalNodes];

        for (int i = 0; i < totalNodes; i++) {
            dist[i] = INF;
            visited[i] = false;
        }
        int startId = start.getX() * cols + start.getY();
        int goalId = goal.getX() * cols + goal.getY();
        dist[startId] = 0;
        while (true) {
            int current = getMinDistanceNode(dist, visited);


            if (current == -1) {
                break;
            }
            if (current == goalId) {
                return dist[current];
            }
            visited[current] = true;
            int x = current / cols;
            int y = current % cols;


            if (x > 0 && isValidAndWalkable(x - 1, y)) {
                int neighborId = getNodeId(x - 1, y);
                processNeighbor(current, neighborId, dist, visited);
            }
            if (x < rows - 1 && isValidAndWalkable(x + 1, y)) {
                int neighborId = getNodeId(x + 1, y);
                processNeighbor(current, neighborId, dist, visited);
            }
            if (y > 0 && isValidAndWalkable(x, y - 1)) {
                int neighborId = getNodeId(x, y - 1);
                processNeighbor(current, neighborId, dist, visited);
            }
            if (y < cols - 1 && isValidAndWalkable(x, y + 1)) {
                int neighborId = getNodeId(x, y + 1);
                processNeighbor(current, neighborId, dist, visited);
            }


        }
        return INF;
    }

    public int getDistance(Position from, Position to) {
        return shortestDistance(from, to);
    }

    public int getDistance(int x1, int y1, int x2, int y2) {
        return shortestDistance(new Position(x1, y1), new Position(x2, y2));
    }

    public int computeHeuristic(Position[] boxPositions, int boxCount,
                                Position[] goalPosition, int goalCount) {
        int total = 0;

        for (int b = 0; b < boxCount; b++) {
            int minDist = INF;
            for (int g = 0; g < goalCount; g++) {
                int d = shortestDistance(boxPositions[b], goalPosition[g]);
                if (d < minDist) {
                    minDist = d;
                }
            }
            if (minDist == INF) {
                return INF;
            }
            total += minDist;
        }
        return total;

    }


}