import Exceptions.InvalidMapException;
import Exceptions.InvalidMatchingOfCrateAndDestTile;
import Exceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

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
    @DisplayName("Load Map(with map2, map3, map4 and map5) - first char is not integer")
    void loadMapFirstCharIsNotInteger() {
        assertThrows(UnknownElementException.class, () -> g.loadMap("tests/badmap2.txt"));
        assertThrows(UnknownElementException.class, () -> g.loadMap("tests/badmap3.txt"));
        assertThrows(UnknownElementException.class, () -> g.loadMap("tests/badmap4.txt"));
        assertThrows(UnknownElementException.class, () -> g.loadMap("tests/badmap5.txt"));
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
        assertFalse(g.makeMove('t'));
        assertFalse(g.isWin());
    }

    @Test
    @DisplayName("Is Player")
    void testIsPlayer() {
        assertTrue(g.isPlayer(1,1));
        assertFalse(g.isPlayer(0, 0));
        assertFalse(g.isPlayer(2, 1));
        assertFalse(g.isPlayer(3, 1));
    }

    @Test
    @DisplayName("Is DeadLock")
    void testIsDeadlock() throws InvalidMapException {
        assertFalse(g.isDeadlocked());

        g.loadMap("tests/testmap1.txt");
        assertTrue(g.isDeadlocked());

        g.loadMap("tests/testmap2.txt");
        assertTrue(g.isDeadlocked());

        g.loadMap("tests/testmap3.txt");
        assertFalse(g.isDeadlocked());
        g.makeMove('d');
        assertFalse(g.isDeadlocked());

        g.loadMap("tests/testmap4.txt");
        assertFalse(g.isDeadlocked());

        g.loadMap("tests/testmap5.txt");
        assertFalse(g.isDeadlocked());
    }

    @Test
    @DisplayName("Display - null pointer")
    void displayNullPointer() {
        g.display();
    }

    //------------------------TA_only_test-----------------------------

    @Test
    void isWin() {
        assertFalse(g.isWin());

        String moves = "swddddddd";
        moves.chars().forEach(x -> g.makeMove((char) x));
        assertTrue(g.isWin());
    }

    @Test
    void isDeadlocked() {
        assertFalse(g.isDeadlocked());

        String moves = "dssawdwdddddddd";
        moves.chars().forEach(x -> g.makeMove((char) x));
        assertTrue(g.isDeadlocked());
    }

    @Test
    void makeMove() {
        assertTrue(g.makeMove('s'));
        assertFalse(g.makeMove('s'));
        assertTrue(g.makeMove('w'));

        assertTrue(g.makeMove('d'));
        assertTrue(g.makeMove('d'));
        assertTrue(g.makeMove('d'));
        assertTrue(g.makeMove('d'));
        assertTrue(g.makeMove('d'));
        assertTrue(g.makeMove('d'));

        assertFalse(g.makeMove('d'));

        assertTrue(g.makeMove('r'));

        assertFalse(g.makeMove('v'));
    }

    @Test
    void testDisplay() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        g.display();
        assertEquals(
                "##########" +
                        "#@...a..A#" +
                        "#b.......#" +
                        "#B.......#" +
                        "##########", outContent.toString().replaceAll("\\R",""));
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


}