package Map.Occupant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private Player p;

    @BeforeEach
    void setup() {
        p = new Player(1,2);
    }

    @Test
    void getRepresentation() {
        //p = new Player(0, 0);
        assertEquals('@', p.getRepresentation());
    }

    @Test
    @DisplayName("Get R")
    void getR() {
        assertEquals(1, p.getR());
    }

    @Test
    @DisplayName("Get C")
    void getC() {
        assertEquals(2, p.getC());
    }

    @Test
    @DisplayName("Set Position")
    void setPosition() {
        p.setPos(11, 22);
        assertEquals(11, p.getR());
        assertEquals(22, p.getC());
    }
}