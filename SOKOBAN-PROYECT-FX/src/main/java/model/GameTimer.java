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
    public void start(){
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }






    @Override
    public void run() {
        while (running) {
            try {


                Thread.sleep(1000);
                stats.addSecond();
                System.out.println("tiempo" + stats.getTime());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    public void stop(){
            running = false;

        }
    public void reset(){

    }
}

