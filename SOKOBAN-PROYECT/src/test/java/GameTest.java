package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testGameCreation() {
        Game game = new Game(null, null);
        assertNotNull(game);
    }
}