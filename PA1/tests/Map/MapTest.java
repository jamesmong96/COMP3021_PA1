package Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Occupant.Crate;
import Map.Occupiable.DestTile;
import Map.Occupiable.Occupiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static Map.Map.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    private Map m;

    private int rows = 5;
    private int cols = 6;
    private char[][] goodMap = {
            {'#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '#'},
            {'.', '@', '.', 'a', 'b', '#'},
            {'#', '.', '.', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'},
    };

    @BeforeEach
    void setUp() throws InvalidMapException {
        m = new Map();
        m.initialize(rows, cols, goodMap);
    }

  /*  @Test
    @DisplayName("Initialize - more than one player")
    void initializeMoreThanOnePlayer() {
        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '@', '@', '.', '#'},
                {'.', '.', '.', 'a', 'b', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        try {
            assertThrows(InvalidMapException.class, m.initialize(rows, cols, testMap1));
        } catch () {

        }
    }*/

    @Test
    @DisplayName("Get DestTile ArrayList")
    void getDestTiles() {
        assertEquals(2, m.getDestTiles().stream().filter(x -> "AB".contains("" + x.getRepresentation())).count());
    }

    @Test
    @DisplayName("Get Crate ArrayList")
    void getCrates() {
        assertEquals(2, m.getCrates().stream().filter(x -> "ab".contains("" + x.getRepresentation())).count());
    }

    @Test
    @DisplayName("Get Cells 2D Array")
    void getCells2DArray() {
        char[][] testMap = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '#'},
                {'.', '@', '.', 'a', 'b', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                assertEquals(m.getCells()[i][j].getRepresentation(), testMap[i][j]);
    }

    @Test
    @DisplayName("Move Player - move to invalid cell")
    void movePlayerToInvalidCell() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', '#', '#', '#', '#', '@'},
                {'#', '.', '.', '.', '.', '#'},
                {'.', '.', '.', 'a', 'b', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertFalse(m.movePlayer(UP));
        assertFalse(m.movePlayer(RIGHT));

        char[][] testMap2 = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '#'},
                {'.', '.', '.', 'a', 'b', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'@', '#', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap2);

        assertFalse(m.movePlayer(DOWN));
        assertFalse(m.movePlayer(LEFT));
    }

    @Test
    @DisplayName("Move Player - move to Wall")
    void movePlayerToWall() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '#', '@', '#'},
                {'.', '.', '.', 'a', '#', '#'},
                {'#', '.', 'b', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertFalse(m.movePlayer(UP));
        assertFalse(m.movePlayer(RIGHT));
        assertFalse(m.movePlayer(DOWN));
        assertFalse(m.movePlayer(LEFT));
    }

    @Test
    @DisplayName("Move Player - move with pushing two crates")
    void movePlayerWithTwoCrates() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', 'A', 'e', '#', '#', '#'},
                {'D', '.', 'a', '.', 'C', '#'},
                {'h', 'd', '@', 'b', 'f', '#'},
                {'#', '.', 'c', '.', 'B', 'H'},
                {'#', '.', 'g', 'E', 'F', 'G'},
        };
        m.initialize(rows, cols, testMap1);

        assertFalse(m.movePlayer(UP));
        assertFalse(m.movePlayer(RIGHT));
        assertFalse(m.movePlayer(DOWN));
        assertFalse(m.movePlayer(LEFT));
    }

    @Test
    @DisplayName("Move Player - move with pushing one crate against wall")
    void movePlayerWithOneCrateAndWall() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', 'A', '#', '#', '#', '#'},
                {'D', '.', 'a', '.', 'C', '#'},
                {'#', 'd', '@', 'b', '#', '#'},
                {'#', '.', 'c', '.', 'B', '#'},
                {'#', '.', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertFalse(m.movePlayer(UP));
        assertFalse(m.movePlayer(RIGHT));
        assertFalse(m.movePlayer(DOWN));
        assertFalse(m.movePlayer(LEFT));
    }

    @Test
    @DisplayName("Move Player - move with pushing one crate against invalid cell")
    void movePlayerWithOneCrateAgainstInvalidCell() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', '#', '#', '#', 'a', '.'},
                {'#', '.', '.', '.', '@', 'b'},
                {'.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', 'A', 'B', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertFalse(m.movePlayer(UP));
        assertFalse(m.movePlayer(RIGHT));

        char[][] testMap2 = {
                {'#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '#'},
                {'.', '.', '.', '.', '.', '#'},
                {'a', '@', '.', 'A', 'B', '#'},
                {'.', 'b', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap2);

        assertFalse(m.movePlayer(DOWN));
        assertFalse(m.movePlayer(LEFT));
    }

    @Test
    @DisplayName("Move Player - move to Tile")
    void movePlayerToTile() {

        //Can use good map
        assertTrue(m.movePlayer(UP));
        assertEquals('@', m.getCells()[1][1].getRepresentation());
        assertEquals('.', m.getCells()[2][1].getRepresentation());

        assertTrue(m.movePlayer(RIGHT));
        assertEquals('@', m.getCells()[1][2].getRepresentation());
        assertEquals('.', m.getCells()[1][1].getRepresentation());

        assertTrue(m.movePlayer(DOWN));
        assertEquals('@', m.getCells()[2][2].getRepresentation());
        assertEquals('.', m.getCells()[1][2].getRepresentation());

        assertTrue(m.movePlayer(LEFT));
        assertEquals('@', m.getCells()[2][1].getRepresentation());
        assertEquals('.', m.getCells()[2][2].getRepresentation());
    }

    @Test
    @DisplayName("Move Player - move to DestTile")
    void movePlayerToDestTile() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', 'A', '#', '#', '#', '#'},
                {'D', '@', 'B', '.', '.', '#'},
                {'.', 'C', 'd', 'a', '.', '#'},
                {'#', '.', 'b', '.', 'c', '#'},
                {'#', '#', '#', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertTrue(m.movePlayer(UP));
        assertEquals('@', m.getCells()[0][1].getRepresentation());
        m.movePlayer(DOWN);
        assertEquals('A', m.getCells()[0][1].getRepresentation());

        assertTrue(m.movePlayer(RIGHT));
        assertEquals('@', m.getCells()[1][2].getRepresentation());
        m.movePlayer(LEFT);
        assertEquals('B', m.getCells()[1][2].getRepresentation());

        assertTrue(m.movePlayer(DOWN));
        assertEquals('@', m.getCells()[2][1].getRepresentation());
        m.movePlayer(UP);
        assertEquals('C', m.getCells()[2][1].getRepresentation());

        assertTrue(m.movePlayer(LEFT));
        assertEquals('@', m.getCells()[1][0].getRepresentation());
        m.movePlayer(RIGHT);
        assertEquals('D', m.getCells()[1][0].getRepresentation());
    }

    @Test
    @DisplayName("Move Player - move with pushing one crate")
    void movePlayerWithOneCrate() throws InvalidMapException {

        //initialize with a suitable test map
        char[][] testMap1 = {
                {'#', 'A', '.', '#', '#', '#'},
                {'D', '.', 'a', '.', 'C', '#'},
                {'.', 'd', '@', 'b', '.', '#'},
                {'#', '.', 'c', '.', 'B', '#'},
                {'#', '.', '.', '#', '#', '#'},
        };
        m.initialize(rows, cols, testMap1);

        assertTrue(m.movePlayer(UP));
        assertEquals('a', m.getCells()[0][2].getRepresentation());
        assertEquals('@', m.getCells()[1][2].getRepresentation());
        m.movePlayer(DOWN);
        assertEquals('a', m.getCells()[0][2].getRepresentation());
        assertEquals('.', m.getCells()[1][2].getRepresentation());
        assertEquals('@', m.getCells()[2][2].getRepresentation());

        assertTrue(m.movePlayer(RIGHT));
        assertEquals('b', m.getCells()[2][4].getRepresentation());
        assertEquals('@', m.getCells()[2][3].getRepresentation());
        m.movePlayer(LEFT);
        assertEquals('b', m.getCells()[2][4].getRepresentation());
        assertEquals('.', m.getCells()[2][3].getRepresentation());
        assertEquals('@', m.getCells()[2][2].getRepresentation());

        assertTrue(m.movePlayer(DOWN));
        assertEquals('c', m.getCells()[4][2].getRepresentation());
        assertEquals('@', m.getCells()[3][2].getRepresentation());
        m.movePlayer(UP);
        assertEquals('c', m.getCells()[4][2].getRepresentation());
        assertEquals('.', m.getCells()[3][2].getRepresentation());
        assertEquals('@', m.getCells()[2][2].getRepresentation());

        assertTrue(m.movePlayer(LEFT));
        assertEquals('d', m.getCells()[2][0].getRepresentation());
        assertEquals('@', m.getCells()[2][1].getRepresentation());
        m.movePlayer(RIGHT);
        assertEquals('d', m.getCells()[2][0].getRepresentation());
        assertEquals('.', m.getCells()[2][1].getRepresentation());
        assertEquals('@', m.getCells()[2][2].getRepresentation());
    }

    @Test
    @DisplayName("Is Occupiable And Not Occupied With Crate")
    void testIsOccupiableAndNotOccupiedWithCrate() {

        //Make sure one cell works
        assertTrue(m.isOccupiableAndNotOccupiedWithCrate(1, 1));
        assertTrue(m.isOccupiableAndNotOccupiedWithCrate(3, 3));
        //Check negative number input
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(-1, 1));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(1, -1));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(-1, -1));
        //Check out of bound
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(10, 0));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(0, 10));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(10, 10));
        //Check wall
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(0, 0));
        //check crate
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(2, 3));
        //check player
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(2, 1));

    }
}