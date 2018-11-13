package TA_only_test.Map.Occupiable;

import Map.Occupant.Crate;
import Map.Occupiable.DestTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DestTileTest {
    private DestTile d;
    private Crate correctCrate;
    private Crate wrongCrate;

    @BeforeEach
    void setUp() {
        d = new DestTile('A');
        correctCrate = new Crate(0, 0, 'a');
        wrongCrate = new Crate(0, 0, 'b');
    }

    @Test
    void isCompletedSuccess() {
        d.setOccupant(correctCrate);
        assertTrue(d.isCompleted());
    }

    @Test
    void isCompletedFailure() {
        d.setOccupant(wrongCrate);
        assertFalse(d.isCompleted());
    }

    @Test
    void isCompletedFailureEmpty() {
        assertFalse(d.isCompleted());
    }

    @Test
    void getRepresentationEmpty() {
        assertEquals('A', d.getRepresentation());
    }

    @Test
    void getRepresentationOccupied() {
        d.setOccupant(correctCrate);
        assertEquals(correctCrate.getID(), d.getRepresentation());
    }

}