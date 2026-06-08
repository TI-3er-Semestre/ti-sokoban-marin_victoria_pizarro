package model;

public class GameTimer implements Runnable{
    // atributos
    private int seconds;
    private boolean running;
    private Thread thread;
    private GameStats stats;

    //constructor
    public GameTimer(GameStats stats){
        this.stats = stats;
        this.running = false;
    }





    @Override
    public void run(){

    }
}
