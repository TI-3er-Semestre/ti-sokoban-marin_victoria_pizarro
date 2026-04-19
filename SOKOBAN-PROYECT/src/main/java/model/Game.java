
package model;

public class Game {

    private Board board;
    private Player player;
    private Level currentLevel;
    private GameStats stats;
    private User user;
    private boolean isFinished;


    public Game(Level level, User user) {
        this.currentLevel = level;
        this.user = user;
        this.isFinished = false;
    }

    /**
     * Inicia el juego inicializando tablero y jugador.
     */
    public void startGame() {
        this.board = currentLevel.getBoard();
        this.player = new Player(new Position(1,1));
        this.stats = new GameStats(currentLevel.getId());
    }

    /**
     * Mueve al jugador considerando colisiones y empuje de cajas.
     * @param direction dirección del movimiento (UP, DOWN, LEFT, RIGHT)
     */
    public void movePlayer(String direction) {

        int offsetX = 0;
        int offsetY = 0;

        switch (direction.toUpperCase()) {
            case "UP":
                offsetY = -1;
                break;
            case "DOWN":
                offsetY = 1;
                break;
            case "LEFT":
                offsetX = -1;
                break;
            case "RIGHT":
                offsetX = 1;
                break;
        }

        int currentPositionX = player.getPosition().getX();
        int currentPositionY = player.getPosition().getY();

        int newX = currentPositionX + offsetX;
        int newY = currentPositionY + offsetY;

        Cell nextCell = board.getCell(newX, newY);

        boolean canMove = true;


        if (nextCell == null) {
            canMove = false;
        } else {
            if (!nextCell.isWalkable()) {
                canMove = false;
            }
        }


        if (canMove) {
            if (nextCell.hasBox()) {

                int newBoxPosX = newX + offsetX;
                int newBoxPosY = newY + offsetY;

                Cell boxTargetCell = board.getCell(newBoxPosX, newBoxPosY);

                boolean canPush = true;

                if (boxTargetCell == null) {
                    canPush = false;
                } else {
                    if (!boxTargetCell.isWalkable()) {
                        canPush = false;
                    }
                    if (boxTargetCell.hasBox()) {
                        canPush = false;
                    }
                }

                if (canPush) {
                    nextCell.setHasBox(false);
                    boxTargetCell.setHasBox(true);
                    stats.addPush();
                } else {
                    canMove = false;
                }
            }
        }


        if (canMove) {
            player.getPosition().setX(newX);
            player.getPosition().setY(newY);
            stats.addMove();
        }
    }


    /**
     * Empuja una caja en una dirección.
     * @param box caja a mover
     * @param direction dirección
     */
    public void pushBox(Box box, String direction) {

        int offsetX = 0;
        int offsetY = 0;

        switch (direction.toUpperCase()) {
            case "UP": offsetY = -1;
            break;
            case "DOWN": offsetY = 1;
            break;
            case "LEFT": offsetX = -1;
            break;
            case "RIGHT": offsetX = 1;
            break;
        }

        int currentX = box.getPosition().getX();
        int currentY = box.getPosition().getY();

        int newX = currentX + offsetX;
        int newY = currentY + offsetY;

        Cell targetCell = board.getCell(newX, newY);

        if (targetCell != null) {
            if (targetCell.isWalkable() && !targetCell.hasBox()) {

                Cell currentCell = board.getCell(currentX, currentY);

                currentCell.setHasBox(false);
                targetCell.setHasBox(true);

                box.getPosition().setX(newX);
                box.getPosition().setY(newY);

                stats.addPush();
            }
        }
    }

    /**
     * Actualiza el estado del juego.
     */
    public void updateGame() {
        if (checkWin()) {
            isFinished = true;
        }
    }

    /**
     * Verifica si el jugador ganó.
     * @return true si el nivel está completo
     */
    public boolean checkWin() {

        return currentLevel.isCompleted();
    }

    /**
     * Reinicia el juego.
     */
    public void resetGame() {
        startGame();
    }

    /**
     * Guarda el juego.
     */
    public void saveGame() {
        System.out.println("Juego Guardado");
    }

    public boolean isFinished() {
        return isFinished;

    }
}