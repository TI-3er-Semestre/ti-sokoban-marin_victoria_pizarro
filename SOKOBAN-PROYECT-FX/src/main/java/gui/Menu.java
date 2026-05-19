package gui;

import model.GameController;

public class Menu {

    private GameController controller;


    public Menu(GameController controller) {
        this.controller = controller;
    }


    public void showMenu() {

    }


    public void selectLevel(int levelId) {

        System.out.println(" Selected Level: " + levelId);
    }


    public void startGame() {

        System.out.println("Starting Game...");
        controller.update();
    }
}