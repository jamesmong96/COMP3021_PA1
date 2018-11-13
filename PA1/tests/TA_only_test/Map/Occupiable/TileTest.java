package TA_only_test.Map.Occupiable;

import Map.Occupant.Crate;
import Map.Occupiable.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile t;
    private Crate crate;

    @BeforeEach
    void setUp() {
        t = new Tile();
        crate = new Crate(0, 0, 'a');
    }

    @Test
    void setAndGetOccupant() {
        t.setOccupant(crate);
        assertTrue(t.getOccupant().isPresent());
        assertSame(crate, t.getOccupant().get());
    }

    @Test
    void removeOccupant() {
        t.setOccupant(crate);
        t.removeOccupant();
        assertFalse(t.getOccupant().isPresent());
    }

    @Test
    void getRepresentationEmpty() {
        assertEquals('.', t.getRepresentation());
    }

    @Test
    void getRepresentationOccupied() {
        t.setOccupant(crate);
        assertEquals(crate.getID(), t.getRepresentation());
    }
}