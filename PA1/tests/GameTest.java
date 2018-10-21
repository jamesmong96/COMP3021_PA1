import Exceptions.InvalidMapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;

    @BeforeEach
    void setUp() throws InvalidMapException {
        g = new Game();
        g.loadMap("tests/goodmap.txt");
    }

    @Test
    void loadMapFailure() throws InvalidMapException {
        g.loadMap("asdlfkjasdlkfj"); //should not throw exception
        assertThrows(InvalidMapException.class, () -> g.loadMap("tests/badmap.txt"));
    }

    @Test
    @DisplayName("Make move with Is Win")
    void testMakeMoveAndIsWin() {
        assertFalse(g.isWin());
        assertFalse(g.makeMove('w'));
        assertFalse(g.makeMove('a'));
        assertTrue(g.makeMove('s'));
        assertFalse(g.makeMove('s'));
        assertTrue(g.makeMove('w'));
        for (int i = 0; i <6; i++)
            assertTrue(g.makeMove('d'));
        assertFalse(g.makeMove('d'));
        assertTrue(g.makeMove('a'));
        assertTrue(g.isWin());
        assertTrue(g.makeMove('r'));
        assertFalse(g.isWin());
    }

}