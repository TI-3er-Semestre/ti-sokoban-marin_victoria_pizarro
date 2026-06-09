package model;

import structures.CustomIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameSaver {

    private static final String SAVE_FILE = "sokoban_save.dat";

    public static void save(Game game) {
        if (game == null || game.getBoard() == null || game.getPlayer() == null) return;
        try {
            int count = game.getBoard().getBoxes().getLength();
            int[] boxX = new int[count];
            int[] boxY = new int[count];
            CustomIterator<Box> it = game.getBoard().getBoxes().iterator();

            int idx = 0;
            while (it.hasNext()) {
                Box box = it.next().getData();
                boxX[idx] = box.getPosition().getX();
                boxY[idx] = box.getPosition().getY();
                idx++;
            }

            GameStats stats = game.getStats();
            int moves  = (stats != null) ? stats.getMoves()  : 0;
            int pushes = (stats != null) ? stats.getPushes() : 0;
            int time   = (stats != null) ? stats.getTime()   : 0;
            String name = (stats != null) ? stats.getPlayerName() : "Desconocido";
            SavedState snapshot = new SavedState(
                    game.getCurrentLevel().getId(),
                    game.getPlayer().getPosition().getX(),
                    game.getPlayer().getPosition().getY(),
                    moves, pushes, time, name, boxX, boxY);
            FileOutputStream fos = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(snapshot);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SavedState load() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SavedState state = (SavedState) ois.readObject();
            ois.close();
            fis.close();
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteSave() {
        File file = new File(SAVE_FILE);
        if (file.exists()) { file.delete(); }
    }

    public static boolean hasSave() {
        return new File(SAVE_FILE).exists();
    }
}