package ui;

import model.GameController;

public class Menu {

    private GameController controller;


    public Menu(GameController controller) {
        this.controller = controller;
    }


    public void showMenu() {

        System.out.println("=== MENÚ ===");
        System.out.println("1. New Game");
        System.out.println("2. choose a level");
        System.out.println("3. Exit");
    }


    public void selectLevel(int levelId) {

        System.out.println(" Selected Level: " + levelId);
    }


    public void startGame() {

        System.out.println("Starting Game...");
        controller.update();
    }
}