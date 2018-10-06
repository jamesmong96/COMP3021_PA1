package Map.Occupiable;

import Map.Occupant.Crate;
import Map.Occupant.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Is Completed Failed with Wrong Crate")
    void isCompletedFailedWithWrongCrate () {
        d.setOccupant(wrongCrate);
        assertFalse(d.isCompleted());
    }

    @Test
    @DisplayName("Is Completed Failed with No Occupant")
    void isCompletedFailedWithNoOccupant () {
        assertFalse(d.isCompleted());
    }

    @Test
    @DisplayName("Is Completed Failed with Not A Crate")
    void isCompletedFailedWithNoCrate () {
        var player = new Player(0,0);

        d.setOccupant(player);
        assertFalse(d.isCompleted());
    }

    @Test
    @DisplayName("Get Representation")
    void getRepresentation() {
        assertEquals('A', d.getRepresentation());
    }

}