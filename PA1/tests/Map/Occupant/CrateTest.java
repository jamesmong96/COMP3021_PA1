package Map.Occupant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrateTest {
    private Crate crate;

    @BeforeEach
    void Setup () {
        crate = new Crate(1, 2, 'a');
    }

    @Test
    @DisplayName("Get Representation")
    void getRepresentation() {
        assertEquals('a', crate.getRepresentation());
    }

    @Test
    @DisplayName("Get R")
    void getR() {
        assertEquals(1, crate.getR());
    }

    @Test
    @DisplayName("Get C")
    void getC() {
        assertEquals(2, crate.getC());
    }

    @Test
    @DisplayName("Set Position")
    void setPosition() {
        crate.setPos(11, 22);
        assertEquals(11, crate.getR());
        assertEquals(22, crate.getC());
    }
}