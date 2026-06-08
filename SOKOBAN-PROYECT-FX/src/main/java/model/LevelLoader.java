package model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class LevelLoader {

    public static Level loadLevel(String path) {
        try {
            Gson gson = new Gson();

            Reader reader = new InputStreamReader(
                    LevelLoader.class.getResourceAsStream(path));
            LevelData data = gson.fromJson(reader, LevelData.class);

            Board board = new Board(data.width, data.height);
            Position playerPosition = null;

            for (int y = 0; y < data.height; y++) {
                String row = data.grid[y];

                for (int x = 0; x < data.width; x++) {
                    char c = row.charAt(x);

                    switch (c) {
                        case '#':
                            board.setCell(x, y, new Cell(CellType.WALL, false));
                            break;

                        case '.':
                            board.setCell(x, y, new Cell(CellType.GOAL, false));
                            break;

                        case '$':
                            board.setCell(x, y, new Cell(CellType.FLOOR, false));
                            board.addBox(new Box(new Position(x, y)));
                            break;

                        case '@':
                            board.setCell(x, y, new Cell(CellType.FLOOR, false));
                            playerPosition = new Position(x, y);
                            break;

                        case '*':
                            board.setCell(x, y, new Cell(CellType.GOAL, false));
                            board.addBox(new Box(new Position(x, y)));
                            break;

                        case '+':
                            board.setCell(x, y, new Cell(CellType.GOAL, false));
                            playerPosition = new Position(x, y);
                            break;

                        default:
                            board.setCell(x, y, new Cell(CellType.FLOOR, false));
                            break;
                    }
                }
            }

            if (playerPosition == null) {
                playerPosition = new Position(1, 1);
            }

            return new Level(data.id, board, playerPosition );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}