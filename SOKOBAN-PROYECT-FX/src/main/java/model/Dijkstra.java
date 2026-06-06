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

}