package ui;

import model.GameController;

public class Menu {

    private GameController controller;


    public Menu(GameController controller) {

        this.controller = controller;
    }

    /**
     * Muestra el menú principal.
     */
    public void showMenu() {
        System.out.println("1. Start Game");
        System.out.println("2. Select Level");
    }


    public void selectLevel(int levelId) {

        System.out.println(" Selected Level: " + levelId);
    }


    public void startGame() {

        System.out.println("Starting Game...");
        controller.update();
    }
}