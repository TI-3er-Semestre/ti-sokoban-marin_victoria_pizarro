package model;

import java.awt.*;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class GameTimer implements Runnable{
    // atributos

    private Thread thread;
    private GameStats stats;
    private volatile boolean running;
    private Label timeLabel;

    //constructor
    public GameTimer(GameStats stats, Label timeLabel){
        this.stats = stats;
        this.timeLabel = timeLabel;
        this.running = false;
    }
    public void start(){
        if (running) {
            return;
        }
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
                int totalSeconds = stats.getTime();
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;

                Platform.runLater(() ->{
                    timeLabel.setText(
                            String.format("%02d:%2d", minutes, seconds)
                    );
                });



            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();


            }


        }
    }
    // detener hilo
    public  void stop(){
            running = false;
            if(thread !=null){
                thread.interrupt();
            }

    }

}

