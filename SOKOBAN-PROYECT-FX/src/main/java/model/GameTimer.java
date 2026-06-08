package model;

public class GameTimer implements Runnable{
    // atributos

    private Thread thread;
    private GameStats stats;
    private volatile boolean running;

    //constructor
    public GameTimer(GameStats stats){
        this.stats = stats;
        this.running = false;
    }
    public void start(){
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void run() {
        while (running) {
            try {


                Thread.sleep(1000);
                stats.addSecond();
                System.out.println("tiempo"  +  stats.getTime());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


        }
    }
    // detener hilo
    public void stop(){
            running = false;
            if(thread !=null){
                thread.interrupt();
            }

    }

}

