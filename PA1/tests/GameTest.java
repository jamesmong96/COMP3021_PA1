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
    @DisplayName("Display - null pointer")
    void displayNullPointer() {

    }


/*    @Test
    @DisplayName("Reload map with the map disappear")
    void reloadMapWithTheMapIsGone() throws InvalidMapException, IOException {

        char[][] map = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '#'},
                {'.', '@', '.', 'a', 'b', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        String filename = "tests/virtualmap.txt";
        File file;
        PrintWriter writer = null;
        int row = map.length;
        int col = map[0].length;

        if (row < 3)
            throw new IllegalArgumentException();

        if (col < 3)
            throw new IllegalArgumentException();

        try {
            file = new File(filename);
            writer = new PrintWriter(file);

            writer.println(row);
            writer.println(col);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++)
                    writer.print(map[i][j]);
                writer.println();
            }

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            writer.close();
        }

        g.loadMap("tests/virtualmap.txt");
        Files.deleteIfExists(Paths.get(new File(".").getAbsolutePath() + File.separator + "tests/virtualmap.txt"));
        assertThrows(NullPointerException.class, () -> g.makeMove('r'));
    }*/

}