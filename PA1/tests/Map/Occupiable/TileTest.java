package Map.Occupiable;

import Map.Occupant.Crate;
import Map.Occupant.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile t;
    private Crate crate;
    private Player player;

    @BeforeEach
    void setUp() {
        t = new Tile();
        crate = new Crate(0, 0, 'a');
        player = new Player(0, 0);
    }

    @Test
    void setAndGetOccupant() {
        t.setOccupant(crate);
        assertTrue(t.getOccupant().isPresent());
        assertSame(crate, t.getOccupant().get());
    }

    @Test
    @DisplayName("Remove Occupant When Crate Exist")
    void removeOccupantWhenCrateExist() {
        t.setOccupant(crate);
        t.removeOccupant();

        assertEquals(Optional.empty(), t.getOccupant());
    }

    @Test
    @DisplayName("Remove Occupant When Player Exist")
    void removeOccupantWhenPlayerExist() {
        t.setOccupant(player);
        t.removeOccupant();

        assertEquals(Optional.empty(), t.getOccupant());
    }

    @Test
    @DisplayName("Remove Occupant When No Occupant Exist")
    void removeOccupantWhenNoOccupantExist() {
        t.removeOccupant();

        assertEquals(Optional.empty(), t.getOccupant());
    }

    @Test
    @DisplayName("Get Occupant When Player Exist")
    void getOccupantWhenPlayerExist () {
        t.setOccupant(player);

        assertTrue(t.getOccupant().isPresent());
        assertEquals(player, t.getOccupant().get());
    }

    @Test
    @DisplayName("Get Occupant When No Occupant")
    void getOccupantWhenNoOccupant() {
        assertFalse(t.getOccupant().isPresent());
        assertEquals(Optional.empty(),t.getOccupant());
    }

    @Test
    @DisplayName("Get Representation Of Crate")
    void getRepresentationOfCrate () {
        t.setOccupant(crate);

        assertEquals('a', t.getRepresentation());
    }

    @Test
    @DisplayName("Get Representation of Player")
    void getRepresentationOfPlayer () {
        t.setOccupant(player);

        assertEquals('@', t.getRepresentation());
    }

    @Test
    @DisplayName("Get Representation When No Occupant")
    void getRepresentationWhenNoOccupant () {
        assertEquals('.', t.getRepresentation());
    }

}