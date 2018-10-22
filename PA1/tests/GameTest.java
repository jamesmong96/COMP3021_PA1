import Exceptions.InvalidMapException;
import Exceptions.InvalidMatchingOfCrateAndDestTile;
import Exceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.IncompleteAnnotationException;
import java.nio.InvalidMarkException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    }

    @Test
    @DisplayName("Display - null pointer")
    void displayNullPointer() {
       // assert
    }



}