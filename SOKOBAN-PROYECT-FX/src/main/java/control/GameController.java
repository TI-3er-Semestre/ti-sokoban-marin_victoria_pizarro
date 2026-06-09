package control;

import model.*;
import structures.CustomIterator;
import structures.HashTable;

public class GameController {

    private Game game;
    private HashTable<String, User> users;
    private User currentUser;

    public GameController() {
        this.users = new HashTable<>(10);
        this.game = null;
        this.currentUser = null;
    }

    public void initGame(Level level1, Level level2, Level level3, User user) {
        this.game = new Game(level1, user);
        this.game.addLevel(level2);
        this.game.addLevel(level3);
        addUser(user);
        setCurrentUser(user);
    }

    public void loadFreshLevel(int levelId) {
        String path = "/assets/levels/level" + levelId + ".json";
        Level fresh = LevelLoader.loadLevel(path);
        if (fresh != null && game != null) {
            game.replaceLevelAndRestart(levelId, fresh);
        } else if (game != null) {
            game.loadLevel(levelId);
        }
    }

    public void restoreSavedState(SavedState saved) {
        if (game == null || saved == null) return;
        game.loadLevel(saved.getLevelId());
        Board board = game.getBoard();
        CustomIterator<Box> it = board.getBoxes().iterator();
        while (it.hasNext()) {
            Box b = it.next().getData();
            board.getCell(b.getPosition().getX(), b.getPosition().getY()).setHasBox(false);
        }
        structures.LinkedList<Box> newBoxes = new structures.LinkedList<>();
        int[] bx = saved.getBoxX();
        int[] by = saved.getBoxY();
        for (int i = 0; i < bx.length; i++) {
            Box box = new Box(new Position(bx[i], by[i]));
            newBoxes.add(box);
            board.getCell(bx[i], by[i]).setHasBox(true);
        }
        board.setBoxes(newBoxes);
        game.getPlayer().getPosition().setX(saved.getPlayerX());
        game.getPlayer().getPosition().setY(saved.getPlayerY());
        GameStats stats = game.getStats();
        if (stats != null) {
            for (int i = 0; i < saved.getMoves(); i++) { stats.addMove(); }
            for (int i = 0; i < saved.getPushes(); i++) { stats.addPush(); }
            stats.setTime(saved.getTime());
        }
    }

    public void setCurrentUser(User user) { this.currentUser = user; }
    public User getCurrentUser() { return currentUser; }

    public void addUser(User user) { users.put(user.getUsername(), user); }
    public User getUser(String username) { return users.get(username); }
    public User removeUser(String username) { return users.remove(username); }
    public boolean userExists(String username) { return users.containsKey(username); }

    public void handleInput(String input) {
        String direction = mapInputToDirection(input);
        if (direction != null) { game.movePlayer(direction); }
    }

    private String mapInputToDirection(String input) {
        switch (input.toLowerCase()) {
            case "w": case "up":    return "UP";
            case "s": case "down":  return "DOWN";
            case "a": case "left":  return "LEFT";
            case "d": case "right": return "RIGHT";
            default: return null;
        }
    }

    public void update() { game.updateGame(); }
    public void restart() { game.resetGame(); }
    public Game getGame() { return game; }
}