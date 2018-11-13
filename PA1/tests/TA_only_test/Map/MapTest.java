package TA_only_test.Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Map;
import Map.Occupant.Crate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

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
    private char[][] mapWithNoPlayers = {
            {'#', '#', '#', '#', '#', '#'},
            {'#', '.', '.', '.', '.', '#'},
            {'#', '.', '.', 'a', 'b', '#'},
            {'#', '.', '.', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'}};
    private char[][] mapWithTwoPlayers = {
            {'#', '#', '#', '#', '#', '#'},
            {'#', '@', '.', '.', '.', '#'},
            {'#', '@', '.', 'a', 'b', '#'},
            {'#', '.', '.', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'}};

    private char[][] mapWithUnknownElement = {
            {'#', '?', '#', '#', '#', '#'},
            {'#', '?', '.', '.', '.', '#'},
            {'#', '?', '.', 'a', 'b', '#'},
            {'#', '.', '.', 'A', 'B', '#'},
            {'#', '#', '#', '#', '#', '#'}};

    @BeforeEach
    void setUp() throws InvalidMapException {
        m = new Map();
        m.initialize(rows, cols, goodMap);
    }

    @Test
    void initialize() {
        assertThrows(InvalidNumberOfPlayersException.class, () -> m.initialize(rows, cols, mapWithNoPlayers));
        assertThrows(InvalidNumberOfPlayersException.class, () -> m.initialize(rows, cols, mapWithTwoPlayers));
        assertThrows(UnknownElementException.class, () -> m.initialize(rows, cols, mapWithUnknownElement));
    }

    @Test
    void getDestTiles() {
        assertEquals(2, m.getDestTiles().stream().filter(x -> "AB".contains("" + x.getRepresentation())).count());
    }

    @Test
    void getCrates() {
        assertEquals(2, m.getCrates().stream().filter(x -> "ab".contains("" + x.getRepresentation())).count());
    }

    @Test
    void getCells() {
        assertNotNull(m.getCrates());
    }

    @Test
    void movePlayer() {
        assertTrue(m.movePlayer(Map.Direction.LEFT));
        assertFalse(m.movePlayer(Map.Direction.LEFT));
        assertTrue(m.movePlayer(Map.Direction.RIGHT));

        assertTrue(m.movePlayer(Map.Direction.RIGHT));
        assertFalse(m.movePlayer(Map.Direction.RIGHT));

        assertTrue(m.movePlayer(Map.Direction.UP));
        assertTrue(m.movePlayer(Map.Direction.RIGHT));
        assertTrue(m.movePlayer(Map.Direction.DOWN));

        assertFalse(m.movePlayer(Map.Direction.DOWN));
        assertFalse(m.movePlayer(Map.Direction.RIGHT));
    }

    @Test
    void moveCrate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Map.class.getDeclaredMethod("moveCrate", Crate.class, Map.Direction.class);
        method.setAccessible(true);

        Optional<Crate> crateA = m.getCrates().stream().filter(x -> x.getRepresentation() == 'a').findFirst();
        Optional<Crate> crateB = m.getCrates().stream().filter(x -> x.getRepresentation() == 'b').findFirst();

        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.UP));
        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.DOWN));

        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.DOWN));
        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.UP));

        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.LEFT));
        assertTrue((boolean) method.invoke(m, crateA.get(), Map.Direction.RIGHT));

        assertFalse((boolean) method.invoke(m, crateA.get(), Map.Direction.RIGHT));

        assertFalse((boolean) method.invoke(m, crateB.get(), Map.Direction.RIGHT));
    }

    @Test
    void isOccupiableAndNotOccupiedWithCrate() {
        assertTrue(m.isOccupiableAndNotOccupiedWithCrate(1, 1));
        assertTrue(m.isOccupiableAndNotOccupiedWithCrate(1, 1));
        assertTrue(m.isOccupiableAndNotOccupiedWithCrate(2, 1)); //occupied with player is ok!
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(-1, -1));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(0, 0));
        assertFalse(m.isOccupiableAndNotOccupiedWithCrate(2, 3));
    }
}